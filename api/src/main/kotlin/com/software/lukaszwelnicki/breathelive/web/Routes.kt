package com.software.lukaszwelnicki.breathelive.web

import com.software.lukaszwelnicki.breathelive.web.handlers.NotifyProcessHandler
import com.software.lukaszwelnicki.breathelive.web.handlers.PollutionInformationHandler
import com.software.lukaszwelnicki.breathelive.web.handlers.UserSubscribeHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router

@Configuration
class Routes(private val pollutionInformationHandler: PollutionInformationHandler,
             private val userSubscribeHandler: UserSubscribeHandler,
             private val notifyProcessHandler: NotifyProcessHandler) {
    @Bean
    fun router() = router {
        "/api".nest {
            accept(APPLICATION_JSON).nest {
                "/pollution".nest {
                    GET("/city/{city}", pollutionInformationHandler::getPollutionByCity)
                    GET("/geo", pollutionInformationHandler::getPollutionByGeolocation)
                }
                "/subscribe".nest {
                    POST("/user", userSubscribeHandler::handleUserSubscriptionRequest)
                }
            }
        }
        "/admin".nest {
            accept(APPLICATION_JSON).nest {
                "/notify".nest {
                    GET("/start", notifyProcessHandler::startNotifyingProcess)
                }
            }
        }
    }
}
