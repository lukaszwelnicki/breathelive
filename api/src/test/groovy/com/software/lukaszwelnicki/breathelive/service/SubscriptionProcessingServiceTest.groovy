package com.software.lukaszwelnicki.breathelive.service

import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.PollutionDto
import com.software.lukaszwelnicki.breathelive.domain.AirPollutionLevel
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import com.software.lukaszwelnicki.breathelive.domain.User
import com.software.lukaszwelnicki.breathelive.dto.BreatheliveProperties
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

class SubscriptionProcessingServiceTest extends Specification {

    def userService = Mock(UserService)
    def emailService = Mock(EmailService)
    def airPollutionLevelService = Mock(AirPollutionLevelService)
    def breatheliveProperties = Mock(BreatheliveProperties)
    def subscriptionProcessingService = new SubscriptionProcessingService(userService, emailService, airPollutionLevelService, breatheliveProperties)

    def "should notify subscribing users"() {
        given:
            Long halfOfSamplingInterval = 5
            Long samplingInterval = 2 * halfOfSamplingInterval
            def now = LocalTime.now()
            def time1 = now.minusSeconds(halfOfSamplingInterval)
            def time2 = now.minusSeconds(samplingInterval)
            def time3 = now.plusSeconds(halfOfSamplingInterval - 1)
            def timesSet1 = new HashSet<>(Arrays.asList(time1, time2))
            def timesSet2 = new HashSet<>(Arrays.asList(time2, time3))
            User user1 = new User(null, "example@mail.com", null, null, new Geolocation(0.0, 0.0), true, timesSet1)
            User user2 = new User(null, "example@mail.com", null, null, new Geolocation(0.0, 0.0), true, timesSet2)
            2 * userService.findAllSubscribingUsers() >> Flux.fromIterable(Arrays.asList(user1, user2))
            2 * airPollutionLevelService.getPollutionByGeolocation(_) >> Mono.just(new PollutionDto(LocalDateTime.now(), "City", new HashMap<String, AirPollutionLevel>()))
            2 * emailService.sendPollutionEmail(_)
            1 * breatheliveProperties.samplingInSecondsAsLong() >> samplingInterval
            VirtualTimeScheduler.getOrSet()
        when:
            def process = subscriptionProcessingService.notifySubscribers()
        then:
            StepVerifier.withVirtualTime({ process })
                    .thenAwait(Duration.ofSeconds(2 * samplingInterval))
                    .expectNextCount(2)
                    .thenCancel()
                    .verify()
            noExceptionThrown()
    }
}
