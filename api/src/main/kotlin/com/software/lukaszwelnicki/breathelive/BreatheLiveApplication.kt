package com.software.lukaszwelnicki.breathelive

import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import com.software.lukaszwelnicki.breathelive.domain.User
import com.software.lukaszwelnicki.breathelive.service.SubscriptionProcessingService
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
class ApplicatonStartup(private val subscriptionProcessingService: SubscriptionProcessingService,
                        private val userService: UserService) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        userService.storeOrUpdateUser(User(id = "1", email = "lukasz.welnicki@gmail.com", geolocation = Geolocation(52.23, 20.99),subscribes = true))
                .then(userService.storeOrUpdateUser(User(id = "2", email = "agnkalis12@wp.pl", firstName = "Agnieszka", geolocation = Geolocation(52.23, 20.99),subscribes = true)))
                .thenMany(subscriptionProcessingService.notifySubscribers())
                .subscribe()
    }
}