package ru.leadpogrommer.airserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AirserverApplication

fun main(args: Array<String>) {
    runApplication<AirserverApplication>(*args)
}
