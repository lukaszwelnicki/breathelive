package com.software.lukaszwelnicki.breathelive.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalTime
import java.time.temporal.ChronoUnit

internal class BreatheliveExtensionsKtTest {

    @ParameterizedTest
    @MethodSource("validateLatitudePositive")
    fun shouldValidateDoubleAsLatitudePositiveTest(lat : Double) {
        Assertions.assertTrue(lat.validateAsLatitude())
    }

    @ParameterizedTest
    @MethodSource("validateLatitudeNegative")
    fun shouldValidateDoubleAsLatitudeNegativeTest(lat : Double) {
        Assertions.assertFalse(lat.validateAsLatitude())
    }

    @ParameterizedTest
    @MethodSource("isWithinTimeWindowFromNowPositive")
    fun shouldGivenTimeBeWithinRangeFromNowPositiveTest(timeWindow : Long, arbitraryTime : LocalTime) {
        Assertions.assertTrue(arbitraryTime.isWithinTimeWindowFromNow(timeWindow, ChronoUnit.SECONDS))
    }

    @ParameterizedTest
    @MethodSource("isWithinTimeWindowFromNowNegative")
    fun shouldGivenTimeBeWithinRangeFromNowNegativeTest(timeWindow : Long, arbitraryTime : LocalTime) {
        Assertions.assertFalse(arbitraryTime.isWithinTimeWindowFromNow(timeWindow, ChronoUnit.SECONDS))
    }

    companion object {
        @JvmStatic
        fun validateLatitudePositive() = listOf(
                Arguments.of(-90.0),
                Arguments.of(0.0),
                Arguments.of(90.0)
        )
        @JvmStatic
        fun validateLatitudeNegative() = listOf(
                Arguments.of(-90.1),
                Arguments.of(90.1)
        )
        @JvmStatic
        fun isWithinTimeWindowFromNowPositive() = listOf(
                Arguments.of(10L, LocalTime.now().minusSeconds(4)),
                Arguments.of(10L, LocalTime.now().plusSeconds(4))
        )
        @JvmStatic
        fun isWithinTimeWindowFromNowNegative() = listOf(
                Arguments.of(10L, LocalTime.now().minusSeconds(6)),
                Arguments.of(10L, LocalTime.now().plusSeconds(6))
        )
    }
}