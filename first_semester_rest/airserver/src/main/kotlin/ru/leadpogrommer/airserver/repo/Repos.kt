package ru.leadpogrommer.airserver.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.leadpogrommer.airserver.entity.AirportsEntity
import ru.leadpogrommer.airserver.entity.BoardingPassesEntity
import ru.leadpogrommer.airserver.entity.BoardingPassesEntityPK
import ru.leadpogrommer.airserver.entity.BookingsEntity
import ru.leadpogrommer.airserver.entity.FlightsVEntity
import ru.leadpogrommer.airserver.entity.PricesEntity
import ru.leadpogrommer.airserver.entity.RoutesEntity
import ru.leadpogrommer.airserver.entity.SeatsEntity
import ru.leadpogrommer.airserver.entity.SeatsEntityPK
import ru.leadpogrommer.airserver.entity.TicketFlightsEntity
import ru.leadpogrommer.airserver.entity.TicketFlightsEntityPK
import ru.leadpogrommer.airserver.entity.TicketsEntity


@Repository
interface RouteRepo: JpaRepository<RoutesEntity, String> {
    fun findAllByDepartureAirport(airport: String): List<RoutesEntity>
    fun findAllByArrivalAirport(airport: String): List<RoutesEntity>
}

@Repository
interface AirportRepo: JpaRepository<AirportsEntity, String> {
    fun findAllByCity(city: String): List<AirportsEntity>
}

@Repository
interface PriceRepo: JpaRepository<PricesEntity, String> {
    @Query("SELECT t FROM PricesEntity t where t.fareConditions = ?1 and t.flightNo = ?2")
    fun findPrice(fareClass: String, flightNo: String): PricesEntity
}

@Repository
interface FlightVRepo: JpaRepository<FlightsVEntity, Int>{

}

@Repository
interface TicketRepo: JpaRepository<TicketsEntity, String>{
    fun getByBookRef(bookRef: String):TicketsEntity
}

@Repository
interface BookingRepo: JpaRepository<BookingsEntity, String>{

}

@Repository
interface TicketFlightRepo: JpaRepository<TicketFlightsEntity, TicketFlightsEntityPK>{
    fun findAllByFlightIdAndFareConditions(flightId: Int, fareConditions: String): List<TicketFlightsEntity>
    fun countByFlightIdAndFareConditions(flightId: Int, fareConditions: String): Int
}

@Repository
interface SeatRepo: JpaRepository<SeatsEntity, SeatsEntityPK>{
    fun getAllByAircraftCodeAndFareConditions(aircraftCode: String, fareConditions: String): List<SeatsEntity>
    fun countByAircraftCodeAndFareConditions(aircraftCode: String, fareConditions: String): Int
}

@Repository
interface BoardingRepo: JpaRepository<BoardingPassesEntity, BoardingPassesEntityPK>{
    fun findAllByFlightId(flightId: Int): List<BoardingPassesEntity>
}