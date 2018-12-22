package com.software.lukaszwelnicki.breathelive.aqicnclient.exceptions

class AqicnServerException(message: String? = "AqicnServerError: ", cause: Throwable?) : Throwable(message, cause)
class GeolocationRequestException(message: String? = "Geolocation request error: ") : Throwable(message)