package com.software.lukaszwelnicki.breathelive

import com.software.lukaszwelnicki.breathelive.subscription.SubscriptionProcessingService
import com.software.lukaszwelnicki.breathelive.users.UserRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "breathelive")
class BreatheliveProperties {
    lateinit var samplingInSeconds: String
    fun samplingInSecondsAsLong(): Long = samplingInSeconds.toLong()
    fun samplingInMinutesAsLong(): Long = samplingInSeconds.toLong() / 60
}

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