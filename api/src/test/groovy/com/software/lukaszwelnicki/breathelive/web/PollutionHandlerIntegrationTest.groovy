package com.software.lukaszwelnicki.breathelive.web

import com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.dto.AqicnDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient(timeout = "36000")
class PollutionHandlerIntegrationTest extends Specification {

    @Autowired
    WebTestClient webTestClient

    def "should retrieve pollution data by city URL"() {
        expect:
            webTestClient.get()
                    .uri("/api/pollution/city/${city}")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(AqicnDto.class)
        where:
            city = "warsaw"
    }

    def "should retrieve pollution data by geolocation URL"() {
        expect:
            webTestClient.get()
                    .uri { builder -> builder.host('localhost')
                        .path('api/pollution/geo')
                        .queryParam('lat', 20.0)
                        .queryParam('lon', 20.0)
                        .build() }
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(AqicnDto.class)
    }

}
