package com.software.lukaszwelnicki.breathelive.web

import com.software.lukaszwelnicki.breathelive.web.handlers.PollutionHandler
import com.software.lukaszwelnicki.breathelive.web.handlers.PollutionStreamHandler
import com.software.lukaszwelnicki.breathelive.web.handlers.PollutionSubscribeHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.web.reactive.function.server.router

@Configuration
class Routes(private val pollutionHandler: PollutionHandler,
             private val pollutionSubscribeHandler: PollutionSubscribeHandler,
             private val pollutionStreamHandler: PollutionStreamHandler
) {
    @Bean
    fun router() = router {
        "/api".nest {
            accept(APPLICATION_JSON).nest {
                "/pollution".nest {
                    GET("/city/{city}", pollutionHandler::getPollutionByCity)
                    GET("/geo", pollutionHandler::getPollutionByGeolocation)
                }
                "/subscribe".nest {
                    POST("/subscribe", pollutionSubscribeHandler::handleUserSubscriptionRequest)
                }
            }
            accept(TEXT_EVENT_STREAM).nest {
                "/pollution/stream".nest {
                    GET("/city/{city}", pollutionStreamHandler::streamPollutionInfoByCity)
                    GET("/geo", pollutionStreamHandler::streamPollutionInfoByGeolocation)
                }
            }
        }
    }
}