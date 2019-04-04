package com.software.lukaszwelnicki.breathelive.web

import com.software.lukaszwelnicki.breathelive.users.UserService
import com.software.lukaszwelnicki.breathelive.web.handlers.PollutionInformationHandler
import com.software.lukaszwelnicki.breathelive.web.handlers.UserSubscribeHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@Configuration
class Routes(private val pollutionInformationHandler: PollutionInformationHandler,
             private val userSubscribeHandler: UserSubscribeHandler,
             private val userService: UserService) {
    @Bean
    fun router() = router {
        "/api".nest {
            accept(APPLICATION_JSON).nest {
                GET("/keepalive", { _ -> ok().contentType(APPLICATION_JSON).body(fromObject("App is alive")) })
                GET("/users", { _ -> ok().contentType(APPLICATION_JSON).body(userService.findAllSubscribingUsers()) })
                "/pollution".nest {
                    GET("/city/{city}", pollutionInformationHandler::getPollutionByCity)
                    GET("/geo", pollutionInformationHandler::getPollutionByGeolocation)
                }
                "/subscribe".nest {
                    POST("/user", userSubscribeHandler::handleUserSubscriptionRequest)
                }
            }
        }
    }
}
