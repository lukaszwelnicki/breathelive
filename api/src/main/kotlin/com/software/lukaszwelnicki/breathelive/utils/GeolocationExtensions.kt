package com.software.lukaszwelnicki.breathelive.utils

import com.software.lukaszwelnicki.breathelive.domain.Geolocation

fun Double.validateAsLatitude(): Boolean = this in -90.0..90.0
fun Double.validateAsLongitude(): Boolean = this in -180.0..180.0

fun Geolocation.validateGeolocation(): Boolean =
        latitude.validateAsLatitude() && longitude.validateAsLongitude()