package com.software.lukaszwelnicki.breathelive

import com.software.lukaszwelnicki.breathelive.service.AirPollutionLevelService
import com.software.lukaszwelnicki.breathelive.service.EmailService
import com.software.lukaszwelnicki.breathelive.service.UserService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@SpringBootApplication
class BreatheLiveApplication

fun main(args: Array<String>) {
    runApplication<BreatheLiveApplication>(*args)
}

@Component
class ApplicatonStartup(private val pollutionLevelService: AirPollutionLevelService,
                        private val userService: UserService,
                        private val emailService: EmailService) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {

    }
}