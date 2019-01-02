package com.software.lukaszwelnicki.breathelive

import com.software.lukaszwelnicki.breathelive.service.SubscriptionProcessingService
import com.software.lukaszwelnicki.breathelive.service.UserRepository
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
class ApplicatonStartup(private val subscriptionProcessingService: SubscriptionProcessingService,
                        private val userRepository: UserRepository) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        userRepository.deleteAll().thenMany(subscriptionProcessingService.notifySubscribers()).subscribe()
    }
}