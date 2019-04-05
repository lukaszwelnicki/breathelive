package com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.url

import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import com.software.lukaszwelnicki.breathelive.extensions.validateGeolocation
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.util.UriBuilder
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "aqicn")
class AqicnNamespace {
    lateinit var scheme: String
    lateinit var host: String
    lateinit var path: String
    lateinit var token: String
}

interface AqicnUrlProvider {
    fun prepareUriForCityRequest(city: String): URI
    fun prepareUriForGeoRequest(geo: Geolocation): URI
}

@Service
class AqicnUrlProviderImpl(private val aqicnNamespace: AqicnNamespace) : AqicnUrlProvider {
    override fun prepareUriForCityRequest(city: String) =
            getBaseUriBuilder()
                    .path(if (city.endsWith("/")) city else "$city/").build()

    override fun prepareUriForGeoRequest(geo: Geolocation): URI =
            geo.takeIf { it.validateGeolocation() }
                    ?.let { "geo:${geo.latitude};${geo.longitude}/" }
                    ?.let { getBaseUriBuilder().path(it).build() }
                    ?: throw IllegalArgumentException("Latitude or longitude outside range")

    private fun getBaseUriBuilder(): UriBuilder =
            UriComponentsBuilder.newInstance()
                    .scheme(aqicnNamespace.scheme)
                    .host(aqicnNamespace.host)
                    .path(aqicnNamespace.path)
                    .queryParam("token", aqicnNamespace.token)
}