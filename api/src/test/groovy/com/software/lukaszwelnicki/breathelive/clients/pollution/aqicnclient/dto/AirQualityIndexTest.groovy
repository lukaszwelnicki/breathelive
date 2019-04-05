package com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.dto

import com.software.lukaszwelnicki.breathelive.domain.AirPollutionLevel
import spock.lang.Specification

class AirQualityIndexTest extends Specification {

    def "should get air pollution level by air quality index"() {
        expect:
            airQualityIndex.airPollutionLevel() == airPollutionLevel
        where:
            airQualityIndex                      || airPollutionLevel
            new AqicnDto.Data.Iaqi.Co(-1)     || AirPollutionLevel.NO_DATA
            new AqicnDto.Data.Iaqi.Co(0)      || AirPollutionLevel.NO_DATA
            new AqicnDto.Data.Iaqi.Co(0.1)    || AirPollutionLevel.GOOD
            new AqicnDto.Data.Iaqi.Co(49.99)  || AirPollutionLevel.GOOD
            new AqicnDto.Data.Iaqi.No2(50)    || AirPollutionLevel.GOOD
            new AqicnDto.Data.Iaqi.No2(99.99) || AirPollutionLevel.MODERATE
            new AqicnDto.Data.Iaqi.O3(100.1)  || AirPollutionLevel.UNHEALTHY_FOR_SENSITIVE_GROUPS
            new AqicnDto.Data.Iaqi.P(151)     || AirPollutionLevel.UNHEALTHY
            new AqicnDto.Data.Iaqi.Pm10(201)  || AirPollutionLevel.VERY_UNHEALTHY
            new AqicnDto.Data.Iaqi.Pm10(1000) || AirPollutionLevel.HAZARDOUS
    }
}
