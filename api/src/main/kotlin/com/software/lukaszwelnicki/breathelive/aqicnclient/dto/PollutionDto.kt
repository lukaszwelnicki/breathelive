package com.software.lukaszwelnicki.breathelive.aqicnclient.dto

import com.software.lukaszwelnicki.breathelive.domain.AirPollutionLevel
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class PollutionDto(val date: LocalDateTime, val city: String, val aqis: Map<String, AirPollutionLevel>)

fun AqicnDto.toPollutionDto() = PollutionDto(LocalDateTime.ofInstant(Instant.ofEpochSecond(data.time.v.toLong()), ZoneId.systemDefault()),
        data.city.name,
        mapOf("pm10" to pm10.airPollutionLevel(), "pm25" to pm25.airPollutionLevel(), "so2" to so2.airPollutionLevel()))

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "aqicn")
class AqicnNamespace {
    lateinit var scheme: String
    lateinit var host: String
    lateinit var path: String
    lateinit var token: String
}
