package com.software.lukaszwelnicki.breathelive.subscription

import com.software.lukaszwelnicki.breathelive.BreatheliveProperties
import com.software.lukaszwelnicki.breathelive.clients.pollution.PollutionService
import com.software.lukaszwelnicki.breathelive.domain.User
import com.software.lukaszwelnicki.breathelive.email.EmailDto
import com.software.lukaszwelnicki.breathelive.email.EmailService
import com.software.lukaszwelnicki.breathelive.extensions.isWithinTimeWindowFromNow
import com.software.lukaszwelnicki.breathelive.users.UserService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.temporal.ChronoUnit

@Service
class SubscriptionProcessingService(private val userService: UserService,
                                    private val emailService: EmailService,
                                    private val airPollutionLevelService: PollutionService,
                                    private val breatheliveProperties: BreatheliveProperties) {

    fun notifySubscribers(): Flux<Unit> {
        val samplingInSeconds = breatheliveProperties.samplingInSecondsAsLong()
        return Flux.interval(Duration.ofSeconds(samplingInSeconds))
                .flatMap { userService.findAllSubscribingUsers() }
                .filter {
                    it.notificationTimes.any { notifTime -> notifTime.isWithinTimeWindowFromNow(samplingInSeconds, ChronoUnit.SECONDS) }
                }
                .flatMap(::getPollutionEmailDataByUser)
                .map(emailService::sendPollutionEmail)
    }

    private fun getPollutionEmailDataByUser(user: User) =
            airPollutionLevelService.getPollutionByGeolocation(user.geolocation)
                    .map { EmailDto(user, it) }
}