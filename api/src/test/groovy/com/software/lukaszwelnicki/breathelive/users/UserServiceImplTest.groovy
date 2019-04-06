package com.software.lukaszwelnicki.breathelive.users

import com.software.lukaszwelnicki.breathelive.TestcontainersConfig
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import com.software.lukaszwelnicki.breathelive.domain.User
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalTime

class UserServiceImplTest extends TestcontainersConfig {

    def user1 = new User(
            "1",
            "email@email.com",
            null,
            null,
            new Geolocation(20.0, 20.0),
            true,
            new HashSet<LocalTime>())
    def user2 = new User(
            "2",
            "email@email.com",
            null,
            null,
            new Geolocation(20.0, 20.0),
            true,
            new HashSet<LocalTime>())


    @Autowired
    UserService userService

    def "should store user correctly"() {
        when:
            userService.storeOrUpdateUser(user1).block()

    }

}
