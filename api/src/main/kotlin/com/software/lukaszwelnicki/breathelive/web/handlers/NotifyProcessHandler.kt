package com.software.lukaszwelnicki.breathelive.web.handlers

import arrow.syntax.function.memoize
import com.software.lukaszwelnicki.breathelive.dto.BreatheliveProperties
import com.software.lukaszwelnicki.breathelive.service.AirPollutionLevelService
import com.software.lukaszwelnicki.breathelive.service.EmailService
import com.software.lukaszwelnicki.breathelive.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class NotifyProcessHandler(
        private val userService: UserService,
        private val properties: BreatheliveProperties,
        private val airPollutionLevelService: AirPollutionLevelService,
        private val emailService: EmailService) {

    fun startNotifyingProcess(req: ServerRequest): Mono<ServerResponse> {
        var pollutionExtractor = airPollutionLevelService.getPollutionByGeolocation
        Flux.interval(Duration.ofSeconds(properties.samplingInSeconds()))
                .map { pollutionExtractor = pollutionExtractor.memoize() }
                .flatMap { userService.findAllSubscribingUsers() }
                .map { it to pollutionExtractor(it.geolocation).block()!! }
                .map { emailService.sendPollutionEmail(it) }.handle { t, u ->  }
    }

}
