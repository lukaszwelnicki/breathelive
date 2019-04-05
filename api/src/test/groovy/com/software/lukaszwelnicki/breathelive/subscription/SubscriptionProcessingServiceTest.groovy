package com.software.lukaszwelnicki.breathelive.subscription

import com.software.lukaszwelnicki.breathelive.BreatheliveProperties
import com.software.lukaszwelnicki.breathelive.clients.pollution.PollutionDto
import com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.AqicnPollutionService
import com.software.lukaszwelnicki.breathelive.domain.AirPollutionLevel
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import com.software.lukaszwelnicki.breathelive.domain.User
import com.software.lukaszwelnicki.breathelive.email.EmailService
import com.software.lukaszwelnicki.breathelive.users.UserService
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
    def airPollutionLevelService = Mock(AqicnPollutionService)
    def breatheliveProperties = Mock(BreatheliveProperties)
    def subscriptionProcessingService = new SubscriptionProcessingService(userService, emailService, airPollutionLevelService, breatheliveProperties)

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

    def "should notify subscribing users"() {
        given:
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

    def "should continue to publish events in case of exception"() {
        given:
            2 * userService.findAllSubscribingUsers() >> Flux.fromIterable(Arrays.asList(user1, user2))
            2 * airPollutionLevelService.getPollutionByGeolocation(_) >> Mono.empty()
            0 * emailService.sendPollutionEmail(_)
            1 * breatheliveProperties.samplingInSecondsAsLong() >> samplingInterval
            VirtualTimeScheduler.getOrSet()
        when:
            def process = subscriptionProcessingService.notifySubscribers()
        then:
            StepVerifier.withVirtualTime({ process })
                    .thenAwait(Duration.ofSeconds(2 * samplingInterval))
                    .expectNextCount(0)
                    .thenCancel()
                    .verify()
            noExceptionThrown()
    }
}
