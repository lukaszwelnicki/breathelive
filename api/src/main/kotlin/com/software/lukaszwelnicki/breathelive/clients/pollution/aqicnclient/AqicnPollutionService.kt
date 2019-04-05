package com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient

import com.software.lukaszwelnicki.breathelive.clients.pollution.PollutionService
import com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.dto.AqicnDto
import com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.url.AqicnUrlProvider
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import com.software.lukaszwelnicki.breathelive.extensions.toPollutionDto
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.net.URI

@Service
class AqicnPollutionService(private val aqicnUrlProvider: AqicnUrlProvider) : PollutionService {

    private val logger = KotlinLogging.logger {}

    private val webClient: WebClient = WebClient.builder()
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .filter(logRequest())
            .build()

    override fun getPollutionByCity(city: String) = Mono.just(aqicnUrlProvider.prepareUriForCityRequest(city))
            .flatMap { getAqicnRequest(it).onErrorResume { Mono.empty() } }
            .map { it.toPollutionDto() }

    override fun getPollutionByGeolocation(geo: Geolocation) = Mono.just(aqicnUrlProvider.prepareUriForGeoRequest(geo))
            .flatMap { getAqicnRequest(it).onErrorResume { Mono.empty() } }
            .map { it.toPollutionDto() }

    private fun getAqicnRequest(uri: URI): Mono<AqicnDto> =
            webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono()

    private fun logRequest() =
            { request: ClientRequest, next: ExchangeFunction ->
                logger.info("Request: {} {}", request.method(), request.url())
                request.headers()
                        .forEach { name, values -> values.forEach { logger.info("{}={}", name, it) } }
                next.exchange(request)
            }
}
