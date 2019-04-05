package com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.service


import com.software.lukaszwelnicki.breathelive.clients.pollution.PollutionDto
import com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.AqicnPollutionService
import com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.url.AqicnNamespace
import com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.url.AqicnUrlProvider
import com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.url.AqicnUrlProviderImpl
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class AqicnPollutionServiceTest extends Specification {

    AqicnNamespace namespace = new AqicnNamespace()
    AqicnUrlProvider urlProvider
    AqicnPollutionService aqicnPollutionService

    def "setup"() {
        namespace.host = 'api.waqi.info'
        namespace.scheme = 'https'
        namespace.path = 'feed/'
        namespace.token = '6752cfb807b996e913f60c4a6cca1209766e45ab'
        urlProvider = new AqicnUrlProviderImpl(namespace)
        aqicnPollutionService = new AqicnPollutionService(urlProvider)
    }

    def "should retreive data from AqicnService by city and not generate any error"() {
        given:
            PollutionDto responseData = aqicnPollutionService.getPollutionByCity("Warsaw").block()
        expect:
            !responseData.airQualityIndicies.isEmpty()
    }

    def "should retreive data by lat = #lat and lon = #lon from AqicnService and not generate any error"() {
        given:
            PollutionDto responseData = aqicnPollutionService.getPollutionByGeolocation(new Geolocation(lat, lon)).block()
        expect:
            !responseData.airQualityIndicies.isEmpty()
        where:
            lat  | lon
            20.0 | 20.0
            50.0 | 60.0
    }

    def "should throw exception when latitude and/or longitude is outside range"() {
        when:
            aqicnPollutionService.getPollutionByGeolocation(new Geolocation(lat, lon)).block()
        then:
            IllegalArgumentException ex = thrown()
            ex.getMessage() == "Latitude or longitude outside range"
        where:
            lat   | lon
            -91.0 | 20.0
            50.0  | 181.0
    }


}
