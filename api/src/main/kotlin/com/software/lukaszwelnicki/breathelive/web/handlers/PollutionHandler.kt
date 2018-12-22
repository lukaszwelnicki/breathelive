package com.software.lukaszwelnicki.breathelive.web.handlers

import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import com.software.lukaszwelnicki.breathelive.service.AirPollutionLevelService
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class PollutionHandler(private val airPollutionLevelService: AirPollutionLevelService) {

    fun getPollutionByCity(req: ServerRequest): Mono<ServerResponse> =
            ok().contentType(APPLICATION_JSON)
                    .body(airPollutionLevelService.getPollutionByCity(req.pathVariable("city")))
                    .doOnError { badRequest().build() }

    fun getPollutionByGeolocation(req: ServerRequest): Mono<ServerResponse> =
            ok().contentType(APPLICATION_JSON)
                    .body(Geolocation(req.queryParam("lat").get().toDouble(),
                            req.queryParam("lon").get().toDouble())
                            .let { airPollutionLevelService.getPollutionByGeolocation(it) })
                    .doOnError { badRequest().build() }

}