package ru.leadpogrommer.airserver.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.leadpogrommer.airserver.repo.AirportRepo
import ru.leadpogrommer.airserver.repo.RouteRepo


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
}