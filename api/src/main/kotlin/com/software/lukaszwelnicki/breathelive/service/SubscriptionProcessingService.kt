package com.software.lukaszwelnicki.breathelive.service

import com.software.lukaszwelnicki.breathelive.domain.User
import com.software.lukaszwelnicki.breathelive.dto.BreatheliveProperties
import com.software.lukaszwelnicki.breathelive.dto.EmailDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration

@Service
class SubscriptionProcessingService(private val userService: UserService,
                                    private val emailService: EmailService,
                                    private val airPollutionLevelService: AirPollutionLevelService,
                                    private val breatheliveProperties: BreatheliveProperties) {

    fun notifySubscribers(): Flux<Unit> {
        return Flux.interval(Duration.ofSeconds(breatheliveProperties.samplingInSecondsAsLong()))
                .flatMap { userService.findAllSubscribingUsers() }
                .flatMap(::getPollutionEmailDataByUser)
                .map(emailService::sendPollutionEmail)
    }

    private fun getPollutionEmailDataByUser(user: User) =
            airPollutionLevelService.getPollutionByGeolocation(user.geolocation)
            .map { EmailDto(user, it) }
}