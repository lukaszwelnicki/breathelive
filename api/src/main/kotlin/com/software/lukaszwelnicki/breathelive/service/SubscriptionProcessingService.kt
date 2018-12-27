package com.software.lukaszwelnicki.breathelive.service

import com.software.lukaszwelnicki.breathelive.dto.BreatheliveProperties
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration

@Service
class SubscriptionProcessingService(private val userService: UserService,
                                    private val emailService: EmailService,
                                    private val airPollutionLevelService: AirPollutionLevelService,
                                    private val breatheliveProperties: BreatheliveProperties) {
    fun notifySubscribers() =
            Flux.interval(Duration.ofSeconds(breatheliveProperties.samplingInSeconds()))
                    .flatMap { userService.findAllSubscribingUsers() }
                    .map {  }
}