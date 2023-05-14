package ru.leadpogrommer.airserver.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*
import ru.leadpogrommer.airserver.repo.AirportRepo
import ru.leadpogrommer.airserver.repo.RouteRepo
import java.time.LocalTime


data class GetCitiesResponseDTO(
    val departureCities: Set<String>,
    val arrivalCities : Set<String>,
)

data class GetAirportsDto(
        val departureAirports: Set<AirportDTO>,
        val arrivalAirports: Set<AirportDTO>,
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

data class LegDTO(
    val departureAirport: String,
    val arrivalAirport: String,
    val departureTime: Long,
    val arrivalTime: Long,
    val flightNo: String,
    val flightId: String
)
data class RouteDTO(
    val totalCost: Int,
    val totalDuration: Long,
    val legs: List<LegDTO>
)

data class BookingDTO(
    val flightIds: List<String>,
    val comfortClass: Int,
)

data class CheckInDTO(
    val bookingId: Int,
    val seat: String,
)

@RestController
@RequestMapping("api")
class Controller(
        val routeRepo: RouteRepo,
        val airportRepo: AirportRepo,
) {
    @Operation(summary = "Get all departure and arrival cities")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK")
    )
    @GetMapping("cities")
    fun getCities(): GetCitiesResponseDTO{
        val routes = routeRepo.findAll()
        return GetCitiesResponseDTO(
                routes.map { it.departureCity }.toSet(),
                routes.map { it.arrivalCity }.toSet()
        )
    }

    @Operation(summary = "Get all departure and arrival airports")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK")
    )
    @GetMapping("airports")
    fun getAirports():  GetAirportsDto {
        val routes = routeRepo.findAll()
        return GetAirportsDto(
                routes.map { AirportDTO(it.departureAirport, it.departureAirportName, it.departureCity) }.toSet(),
                routes.map { AirportDTO(it.arrivalAirport, it.arrivalAirportName, it.arrivalCity) }.toSet()
        )
    }

    @Operation(summary = "Get all airports within a city")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(
            responseCode = "404",
            description = "The city does not exist",
            content = [
                Content()
            ]
            )
    )
    @GetMapping("airport/{city}")
    fun getAirportsInCity(@PathVariable city: String): List<AirportDTO>{
        return airportRepo.findAllByCity(city).map { AirportDTO(it.airportCode, it.airportName, it.city) }
    }


    @GetMapping("schedule/inbound/{airport}")
    @Operation(summary = "Get airport inbound schedule")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "404", description = "The airport does not exist", content = [Content()])
    )
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
    @Operation(summary = "Get airport outbound schedule")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "404", description = "The airport does not exist", content = [Content()])
    )
    fun getOuboundSchedule(@PathVariable airport: String): List<ScheduleEntryDTO>{
        val routes = routeRepo.findAllByDepartureAirport(airport)
        return routes.map { ScheduleEntryDTO(
            it.daysOfWeek.toList(),
            TimeDTO(it.departure),
            it.arrivalAirport,
            it.flightNo
        ) }
    }


    @GetMapping("routes/{source}/{destination}/{date}")
    @Operation(summary = "Find routes between two points")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "404", description = "Source or destination does not exist", content = [Content()])
    )
    fun getRoutes(
        @Parameter(description = "Source airport or city") @PathVariable source: String,
        @Parameter(description = "Destination airport or city") @PathVariable destination: String,
        @Parameter(description = "Departure date in format YYYY-MM-DD") @PathVariable date: String,
        @Parameter(description = "0 - economy, 1 - comfort, 2 - business")
        @RequestParam(required = false, defaultValue = "0") comfortClass: Int,
        @Parameter(description = "Max leg count, 0 = unbound (10)")
        @RequestParam(required = false, defaultValue = "1") legCount: Int
    ): RouteDTO{
        TODO()
    }

    @PostMapping("booking")
    @Operation(summary = "Create booking for route")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "404", description = "Invalid flight IDs", content = [Content()]),
        ApiResponse(responseCode = "409", description = "No free seats", content = [Content()])
    )
    fun createBooking(@RequestBody route: BookingDTO): Int{
        TODO()
    }

    @PostMapping("checkin")
    @Operation(summary = "Check-in for a flight")
    @ApiResponses(
        ApiResponse(description = "OK", responseCode = "200"),
        ApiResponse(description = "Wrong booking id or seat", responseCode = "404")
    )
    fun checkin(@RequestBody data: CheckInDTO){
        TODO()
    }


}