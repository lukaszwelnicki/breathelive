package com.software.lukaszwelnicki.breathelive.users

import com.software.lukaszwelnicki.breathelive.TestcontainersConfig
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import com.software.lukaszwelnicki.breathelive.domain.User
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalTime

class UserServiceImplTest extends TestcontainersConfig {

    def user1 = new User(
            "1",
            "EMAIL@EMAIL.com",
            null,
            null,
            new Geolocation(20.0, 20.0),
            true,
            new HashSet<LocalTime>())
    def user2 = new User(
            "2",
            "EMAIL@EMAIL.com",
            null,
            null,
            new Geolocation(20.0, 20.0),
            true,
            new HashSet<LocalTime>())
    def user3 = new User(
            "3",
            "email2@EMAIL.com",
            null,
            null,
            new Geolocation(20.0, 20.0),
            true,
            new HashSet<LocalTime>())
    def user4 = new User(
            "4",
            "email3@EMAIL.com",
            null,
            null,
            new Geolocation(20.0, 20.0),
            false,
            new HashSet<LocalTime>())


    @Autowired
    UserService userService

    def "should store user correctly"() {
        when:
            userService.storeOrUpdateUser(user1).block()
        then:
            userService.findUserByEmail(user1.email).block() != null
    }

    def "should override user with the same email"() {
        when:
            userService.storeOrUpdateUser(user1).block()
            userService.storeOrUpdateUser(user2).block()
        then:
            userService.findAllSubscribingUsers().toIterable().size() == 1
    }

    def "should find all subscribing users"() {
        when:
            userService.storeOrUpdateUser(user1).block()
            userService.storeOrUpdateUser(user2).block()
            userService.storeOrUpdateUser(user3).block()
            userService.storeOrUpdateUser(user4).block()
        then:
            userService.findAllSubscribingUsers().toIterable().size() == 2
    }
}
