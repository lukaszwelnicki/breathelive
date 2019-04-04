package com.software.lukaszwelnicki.breathelive.web.handlers

import com.software.lukaszwelnicki.breathelive.domain.User
import com.software.lukaszwelnicki.breathelive.users.UserService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class UserSubscribeHandler(private val userService: UserService) {

    fun handleUserSubscriptionRequest(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(User::class.java)
                .flatMap(::saveAndRespond)
    }

    private fun saveAndRespond(user: User) = ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(userService.storeOrUpdateUser(user))
}