package com.software.lukaszwelnicki.breathelive.clients.pollution.aqicnclient.dto

import com.software.lukaszwelnicki.breathelive.domain.AirPollutionLevel

interface AirQualityIndex {
    val v: Double
    fun airPollutionLevel() = AirPollutionLevel.values().first { v in it.lowerAqi..it.upperAqi }
}

data class AqicnDto(val data: Data = Data(), val status: String = "") {
    val pm10 get() = data.iaqi.pm10
    val pm25 get() = data.iaqi.pm25
    val so2 get() = data.iaqi.so2

    data class Data(
            val aqi: Int = 0,
            val attributions: List<Attribution> = listOf(),
            val city: City = City(),
            val debug: Debug = Debug(),
            val dominentpol: String = "",
            val iaqi: Iaqi = Iaqi(),
            val idx: Int = 0,
            val time: Time = Time()) {
        data class City(
                val geo: List<Double> = listOf(),
                val name: String = "",
                val url: String = "")

        data class Attribution(
                val name: String = "",
                val url: String = "")

        data class Time(
                val s: String = "",
                val tz: String = "",
                val v: Int = 0)

        data class Debug(val sync: String = "")

        data class Iaqi(
                val co: Co = Co(),
                val no2: No2 = No2(),
                val o3: O3 = O3(),
                val p: P = P(),
                val pm10: Pm10 = Pm10(),
                val pm25: Pm25 = Pm25(),
                val so2: So2 = So2(),
                val t: T = T(),
                val w: W = W()) {

            data class O3(override val v: Double = -1.0) : AirQualityIndex

            data class Pm25(override val v: Double = -1.0) : AirQualityIndex

            data class T(override val v: Double = -1.0) : AirQualityIndex

            data class Co(override val v: Double = -1.0) : AirQualityIndex

            data class So2(override val v: Double = -1.0) : AirQualityIndex

            data class P(override val v: Double = -1.0) : AirQualityIndex

            data class W(override val v: Double = -1.0) : AirQualityIndex

            data class Pm10(override val v: Double = -1.0) : AirQualityIndex

            data class No2(override val v: Double = -1.0) : AirQualityIndex
        }
    }

    companion object Factory {
        @JvmStatic
        fun getDummyInstance(): AqicnDto {
            return AqicnDto(Data(iaqi = Data.Iaqi(
                    pm10 = Data.Iaqi.Pm10(10.0),
                    pm25 = Data.Iaqi.Pm25(10.0),
                    so2 = Data.Iaqi.So2(10.0))))
        }
    }
}