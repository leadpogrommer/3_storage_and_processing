package ru.leadpogrommer.airserver.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.leadpogrommer.airserver.entity.RoutesEntity
import ru.leadpogrommer.airserver.repo.AirportRepo
import ru.leadpogrommer.airserver.repo.RouteRepo
import java.sql.Time
import java.time.LocalTime


data class GetCitiesResponseDTO(
    val departureCities: Set<String>,
    val arrivalCities : Set<String>,
)

data class GetAirportsDto(
        val departureCities: Set<AirportDTO>,
        val arrivalCities : Set<AirportDTO>,
)

data class AirportDTO(
        val code: String,
        val name: String,
        val city: String
)

data class ScheduleEntryDTO(
    var daysOfWeek: List<Int>,
    var time: TimeDTO,
    var otherAirport: String,
    var flightNo: String,
)

data class TimeDTO(
    var hours: Int,
    var minutes: Int,
){
    constructor(t: LocalTime) : this(0,0) {
        this.hours = t.hour
        this.minutes = t.minute
    }
}

@RestController
@RequestMapping("api")
class Controller(
        val routeRepo: RouteRepo,
        val airportRepo: AirportRepo,
) {
    @GetMapping("cities")
    fun getCities(): GetCitiesResponseDTO{
        val routes = routeRepo.findAll()
        return GetCitiesResponseDTO(
                routes.map { it.departureCity }.toSet(),
                routes.map { it.arrivalCity }.toSet()
        )
    }

    @GetMapping("airports")
    fun getAirports():  GetAirportsDto {
        val routes = routeRepo.findAll()
        return GetAirportsDto(
                routes.map { AirportDTO(it.departureAirport, it.departureAirportName, it.departureCity) }.toSet(),
                routes.map { AirportDTO(it.arrivalAirport, it.arrivalAirportName, it.arrivalCity) }.toSet()
        )
    }

    @GetMapping("airport/{city}")
    fun getAirportsInCity(@PathVariable city: String): List<AirportDTO>{
        return airportRepo.findAllByCity(city).map { AirportDTO(it.airportCode, it.airportName, it.city) }
    }

    /*
    Days of week
Time of arrival
Flight no
Origin

     */

    @GetMapping("schedule/inbound/{airport}")
    fun getInboundSchedule(@PathVariable airport: String): List<ScheduleEntryDTO>{
        val routes = routeRepo.findAllByArrivalAirport(airport)
        return routes.map { ScheduleEntryDTO(
            it.daysOfWeek.toList(),
            TimeDTO(it.arrival),
            it.departureAirport,
            it.flightNo
        ) }
    }

    @GetMapping("schedule/outbound/{airport}")
    fun getOuboundSchedule(@PathVariable airport: String): List<ScheduleEntryDTO>{
        val routes = routeRepo.findAllByDepartureAirport(airport)
        return routes.map { ScheduleEntryDTO(
            it.daysOfWeek.toList(),
            TimeDTO(it.departure),
            it.arrivalAirport,
            it.flightNo
        ) }
    }
}