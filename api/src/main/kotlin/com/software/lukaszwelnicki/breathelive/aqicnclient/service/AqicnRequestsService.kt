package com.software.lukaszwelnicki.breathelive.aqicnclient.service

import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.AqicnDto
import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.AqicnNamespace
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import com.software.lukaszwelnicki.breathelive.utils.validateGeolocation
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.UriBuilder
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.net.URI

@Service
class AqicnRequestsService(private val aqicnNamespace: AqicnNamespace) {

    private val logger = KotlinLogging.logger {}

    private val webClient: WebClient = WebClient.builder()
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .filter(logRequest())
            .build()

    fun getPollutionByCity(city: String) = Mono.just(prepareUriForCityRequest(city))
            .flatMap { it -> getAqicnRequest(it).onErrorResume { Mono.empty() } }

    fun getPollutionByGeolocation(geo: Geolocation) = Mono.just(prepareUriForGeoRequest(geo))
            .flatMap { it -> getAqicnRequest(it).onErrorResume { Mono.empty() } }

    private fun getAqicnRequest(uri: URI): Mono<AqicnDto> =
            webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono()

    private fun prepareUriForCityRequest(city: String) =
            getBaseUriBuilder()
                    .path(if (city.endsWith("/")) city else "$city/").build()

    private fun prepareUriForGeoRequest(geo: Geolocation): URI =
            geo.takeIf { it.validateGeolocation() }
                    ?.let { "geo:${geo.latitude};${geo.longitude}/" }
                    ?.let { getBaseUriBuilder().path(it).build() }
                    ?: throw IllegalArgumentException("Latitude or longitude outside range")

    private fun getBaseUriBuilder(): UriBuilder =
            UriComponentsBuilder.newInstance()
                    .scheme(aqicnNamespace.scheme)
                    .host(aqicnNamespace.host)
                    .path(aqicnNamespace.path)
                    .queryParam("token", aqicnNamespace.token)

    private fun logRequest() =
            { request: ClientRequest, next: ExchangeFunction ->
                logger.info("Request: {} {}", request.method(), request.url())
                request.headers()
                        .forEach { name, values -> values.forEach { logger.info("{}={}", name, it) } }
                next.exchange(request)
            }
}
