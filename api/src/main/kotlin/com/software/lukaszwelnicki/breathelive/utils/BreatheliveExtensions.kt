package com.software.lukaszwelnicki.breathelive.utils

import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import java.time.LocalTime
import java.time.temporal.TemporalUnit

fun Double.validateAsLatitude(): Boolean = this in -90.0..90.0
fun Double.validateAsLongitude(): Boolean = this in -180.0..180.0

fun Geolocation.validateGeolocation(): Boolean =
        latitude.validateAsLatitude() && longitude.validateAsLongitude()

fun LocalTime.isWithinTimeWindowFromNow(timeWindow: Long, temporalUnit: TemporalUnit): Boolean {
    val now = LocalTime.now()
    return this.isAfter(now.minus(timeWindow / 2, temporalUnit)) && this.isBefore(now.plus(timeWindow / 2, temporalUnit))
}