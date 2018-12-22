package com.software.lukaszwelnicki.breathelive.web.handlers

import com.software.lukaszwelnicki.breathelive.service.AirPollutionLevelService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class PollutionStreamHandler(private val airPollutionLevelService: AirPollutionLevelService) {

    fun streamPollutionInfoByCity(req: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().build()
    }

    fun streamPollutionInfoByGeolocation(req: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().build()
    }

}