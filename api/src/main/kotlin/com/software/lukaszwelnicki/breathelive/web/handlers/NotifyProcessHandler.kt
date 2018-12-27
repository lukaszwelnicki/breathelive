package com.software.lukaszwelnicki.breathelive.web.handlers

import com.software.lukaszwelnicki.breathelive.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class NotifyProcessHandler(private val userService: UserService) {

    fun startNotifyingProcess(req: ServerRequest): Mono<ServerResponse> {
        Flux.
    }


}