package com.software.lukaszwelnicki.breathelive.aqicnclient.service

import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.AqicnDto
import com.software.lukaszwelnicki.breathelive.aqicnclient.namespace.AqicnNamespace
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class AqicnRequestsServiceTest extends Specification {

    AqicnNamespace namespace = new AqicnNamespace()
    AqicnRequestsService requestsService

    def "setup"() {
        namespace.host = 'api.waqi.info'
        namespace.scheme = 'https'
        namespace.path = 'feed/'
        namespace.token = '6752cfb807b996e913f60c4a6cca1209766e45ab'
        requestsService = new AqicnRequestsService(namespace)
    }

    def "should retreive data from AqicnService by city and not generate any error"() {
        given:
            AqicnDto responseData = requestsService.getMeasurementsByCity("Warsaw").block()
        expect:
            responseData.status != ''
    }

    def "should retreive data by lat = #lat and lon = #lon from AqicnService and not generate any error"() {
        given:
            AqicnDto responseData = requestsService.getMeasurementsByGeo(new Geolocation(lat, lon)).block()
        expect:
            responseData.status != ''
        where:
            lat  | lon
            20.0 | 20.0
            50.0 | 60.0
    }

    def "should throw exception when latitude and/or longitude is outside range"() {
        when:
            requestsService.getMeasurementsByGeo(new Geolocation(lat, lon)).block()
        then:
            IllegalArgumentException ex = thrown()
            ex.getMessage() == "Latitude or longitude outside range"
        where:
            lat   | lon
            -91.0 | 20.0
            50.0  | 181.0
    }


}
