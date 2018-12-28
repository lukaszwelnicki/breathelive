package com.software.lukaszwelnicki.breathelive.service

import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.AqicnDto
import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.PollutionDto
import com.software.lukaszwelnicki.breathelive.aqicnclient.service.AqicnRequestsService
import com.software.lukaszwelnicki.breathelive.domain.AirPollutionLevel
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import reactor.core.publisher.Mono
import spock.lang.Specification

class AirPollutionLevelServiceTest extends Specification {

    def aqicnRequestsService = Mock(AqicnRequestsService)
    def airPollutionLevelService = new AirPollutionLevelService(aqicnRequestsService)

    def "should get pollution level dto by geolocation"() {
        when:
            PollutionDto pollutionDto = airPollutionLevelService.getPollutionByGeolocation(new Geolocation(20.0, 20.0)).block()
        then:
            1 * aqicnRequestsService.getPollutionByGeolocation(_) >> Mono.just(AqicnDto.getDummyInstance())
            pollutionDto.aqis.entrySet().every { entry -> entry.value == AirPollutionLevel.GOOD }
    }

    def "should get pollution level dto by city"() {
        when:
            PollutionDto pollutionDto = airPollutionLevelService.getPollutionByCity("city").block()
        then:
            1 * aqicnRequestsService.getPollutionByCity(_) >> Mono.just(AqicnDto.getDummyInstance())
            pollutionDto.aqis.entrySet().every { entry -> entry.value == AirPollutionLevel.GOOD }
    }


}
