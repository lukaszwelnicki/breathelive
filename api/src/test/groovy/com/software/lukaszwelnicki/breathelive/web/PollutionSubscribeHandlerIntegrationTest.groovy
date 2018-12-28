package com.software.lukaszwelnicki.breathelive.web

import com.software.lukaszwelnicki.breathelive.TestcontainersConfig
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import com.software.lukaszwelnicki.breathelive.domain.User
import com.software.lukaszwelnicki.breathelive.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@AutoConfigureWebTestClient(timeout = "36000")
class PollutionSubscribeHandlerIntegrationTest extends TestcontainersConfig {

    @Autowired
    WebTestClient webTestClient

    @Autowired
    UserService userService


    def "subscribing user should be stored to database"() {
        given:
            User user = new User(null, email, firstName, lastName, new Geolocation(0.0, 0.0), true)
        expect:
            webTestClient.post()
                    .uri("/api/subscribe/user")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .body(Mono.just(user), User.class)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(User.class)
            userService.findUserByEmail(email).block().email == email
        where:
            email = 'example.email@example.com'
            firstName = 'John'
            lastName = 'Smith'


    }
}
