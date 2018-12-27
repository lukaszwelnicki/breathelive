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
    GOOD(0.0, 50.0),
    MODERATE(51.0, 100.0),
    UNHEALTHY_FOR_SENSITIVE_GROUPS(101.0, 150.0),
    UNHEALTHY(151.0, 200.0),
    VERY_UNHEALTHY(201.0, 250.0),
    HAZARDOUS(251.0, Double.MAX_VALUE)
}