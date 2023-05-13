package ru.leadpogrommer.airserver.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.leadpogrommer.airserver.entity.AirportsEntity
import ru.leadpogrommer.airserver.entity.RoutesEntity


@Repository
interface RouteRepo: JpaRepository<RoutesEntity, String> {
    fun findAllByDepartureAirport(airport: String): List<RoutesEntity>
    fun findAllByArrivalAirport(airport: String): List<RoutesEntity>
}

@Repository
interface AirportRepo: JpaRepository<AirportsEntity, String> {
    fun findAllByCity(city: String): List<AirportsEntity>
}