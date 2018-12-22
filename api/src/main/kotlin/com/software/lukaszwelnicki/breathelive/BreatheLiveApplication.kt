package com.software.lukaszwelnicki.breathelive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BreatheLiveApplication

fun main(args: Array<String>) {
    runApplication<BreatheLiveApplication>(*args)
}
