package com.software.lukaszwelnicki.breathelive.web

import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.AqicnDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@AutoConfigureWebTestClient
@SpringBootTest
class PollutionRoutesTest extends Specification {

    @Autowired
    WebTestClient webClient

    def "should retreive air pollution data by city"() {
        expect:
            webClient.get()
                    .uri("/api/pollution/city/" + city)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(AqicnDto.class)
        where:
            city = "shanghai"
    }
}
