package com.software.lukaszwelnicki.breathelive.aqicnclient.dto

import com.software.lukaszwelnicki.breathelive.domain.AirPollutionLevel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class PollutionDto(val date: LocalDateTime, val city: String, val aqis: Map<AirQualityIndex, AirPollutionLevel>)

fun AqicnDto.toPollutionDto() = PollutionDto(LocalDateTime.ofInstant(Instant.ofEpochSecond(data.time.v.toLong()), ZoneId.systemDefault()),
        data.city.name,
        mapOf(pm10 to pm10.airPollutionLevel(), pm25 to pm25.airPollutionLevel(), so2 to so2.airPollutionLevel()))