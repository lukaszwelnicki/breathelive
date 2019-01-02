package com.software.lukaszwelnicki.breathelive.service

import com.software.lukaszwelnicki.breathelive.domain.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRepository : ReactiveMongoRepository<User, String> {
    fun findByEmail(email: String): Mono<User>
    fun deleteByEmail(email: String): Mono<Long>
    fun findAllBySubscribesTrue(): Flux<User>
}

interface UserService {
    fun storeOrUpdateUser(user: User): Mono<User>
    fun findUserByEmail(email: String): Mono<User>
    fun findAllSubscribingUsers(): Flux<User>
}

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun storeOrUpdateUser(user: User) = userRepository.deleteByEmail(user.email).then(userRepository.insert(user))
    override fun findUserByEmail(email: String) = userRepository.findByEmail(email)
    override fun findAllSubscribingUsers() = userRepository.findAllBySubscribesTrue()
}