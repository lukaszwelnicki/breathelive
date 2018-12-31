package com.software.lukaszwelnicki.breathelive.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document
@TypeAlias("user")
data class User(
    @Id
    val id: String?,
    val email: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val geolocation: Geolocation,
    val subscribes: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        return email.hashCode()
    }
}

data class Geolocation(val latitude: Double, val longitude: Double)

enum class AirPollutionLevel(val lowerAqi: Double, val upperAqi: Double) {
    NO_DATA(Double.NEGATIVE_INFINITY, 0.0),
    GOOD(0.0, 50.0),
    MODERATE(50.0, 100.0),
    UNHEALTHY_FOR_SENSITIVE_GROUPS(100.0, 150.0),
    UNHEALTHY(150.0, 200.0),
    VERY_UNHEALTHY(200.0, 250.0),
    HAZARDOUS(250.0, Double.POSITIVE_INFINITY)
}