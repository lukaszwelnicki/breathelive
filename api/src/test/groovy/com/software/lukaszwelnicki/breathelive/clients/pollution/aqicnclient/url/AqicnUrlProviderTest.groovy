package com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.url

import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import spock.lang.Specification

class AqicnUrlProviderTest extends Specification {

    public static final String CITY = "Warsaw"
    public static final String CITY_WITH_SLASH = "Warsaw/"

    final AqicnNamespace namespace = new AqicnNamespace();
    AqicnUrlProvider urlProvider;

    def "setup"() {
        namespace.host = 'api.waqi.info'
        namespace.scheme = 'https'
        namespace.path = 'feed/'
        namespace.token = '6752cfb807b996e913f60c4a6cca1209766e45ab'
        urlProvider = new AqicnUrlProviderImpl(namespace)
    }

    def "should prepare correct URI for city request"() {
        given:
            String expectedURI = "https://api.waqi.info/feed/${-> CITY}/?token=6752cfb807b996e913f60c4a6cca1209766e45ab"
        expect:
            urlProvider.prepareUriForCityRequest(CITY) == new URI(expectedURI)
            urlProvider.prepareUriForCityRequest(CITY_WITH_SLASH) == new URI(expectedURI)
    }

    def "should prepare correct URI for geo request"() {
        given:
            Geolocation geo = new Geolocation(20.0, 30.0)
            String expectedURI = "https://api.waqi.info/feed/geo:${geo.latitude};${geo.longitude}/?token=6752cfb807b996e913f60c4a6cca1209766e45ab"
        expect:
            urlProvider.prepareUriForGeoRequest(geo) == new URI(expectedURI)
    }

    def "should throw exception for invalid geolocation"() {
        given:
            Geolocation geo = new Geolocation(lat, lon)
        when:
            urlProvider.prepareUriForGeoRequest(geo)
        then:
            IllegalArgumentException ex = thrown()
            ex.getMessage() == "Latitude or longitude outside range"
        where:
            lat   || lon
            1000  || 20
            20    || 1000
            -1000 || 20
            20    || -1000
            1000  || 1000
            -1000 || -1000
    }

}
