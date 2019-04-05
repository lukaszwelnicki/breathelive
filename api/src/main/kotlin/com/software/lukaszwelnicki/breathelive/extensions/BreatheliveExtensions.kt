package com.software.lukaszwelnicki.breathelive.extensions

import com.software.lukaszwelnicki.breathelive.clients.pollution.PollutionDto
import com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.dto.AqicnDto
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.TemporalUnit

fun Double.validateAsLatitude(): Boolean = this in -90.0..90.0
fun Double.validateAsLongitude(): Boolean = this in -180.0..180.0

fun LocalTime.isWithinTimeWindowFromNow(timeWindow: Long, temporalUnit: TemporalUnit): Boolean {
    val now = LocalTime.now()
    return this.isAfter(now.minus(timeWindow / 2, temporalUnit)) && this.isBefore(now.plus(timeWindow / 2, temporalUnit))
}

fun Geolocation.validateGeolocation(): Boolean =
        latitude.validateAsLatitude() && longitude.validateAsLongitude()

fun AqicnDto.toPollutionDto() = PollutionDto(LocalDateTime.ofInstant(Instant.ofEpochSecond(data.time.v.toLong()), ZoneId.systemDefault()),
        data.city.name,
        mapOf("pm10" to pm10.airPollutionLevel(), "pm25" to pm25.airPollutionLevel(), "so2" to so2.airPollutionLevel()))
