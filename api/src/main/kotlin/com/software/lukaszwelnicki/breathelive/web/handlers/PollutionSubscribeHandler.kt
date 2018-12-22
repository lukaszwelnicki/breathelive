package com.software.lukaszwelnicki.breathelive.web.handlers

import com.software.lukaszwelnicki.breathelive.service.AirPollutionLevelService
import com.software.lukaszwelnicki.breathelive.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class PollutionSubscribeHandler(private val userService: UserService) {

    fun handleUserSubscriptionRequest(req: ServerRequest): Mono<ServerResponse> {
        return Mono.empty()
    }

}