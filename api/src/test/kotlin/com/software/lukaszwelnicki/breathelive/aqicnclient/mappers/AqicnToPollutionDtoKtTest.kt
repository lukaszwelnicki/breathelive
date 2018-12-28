package com.software.lukaszwelnicki.breathelive.aqicnclient.mappers

import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.AqicnDto
import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.toPollutionDto
import com.software.lukaszwelnicki.breathelive.domain.AirPollutionLevel
import org.junit.Test
import org.junit.jupiter.api.Assertions

internal class AqicnToPollutionDtoKtTest {
    private val cityName = "Brodnica"
    private val pollutionValue = 15.0
    private val aqicnDto = AqicnDto(
            data = AqicnDto.Data(
                    city = AqicnDto.Data.City(
                            name = cityName
                    ),
                    iaqi = AqicnDto.Data.Iaqi(
                            pm10 = AqicnDto.Data.Iaqi.Pm10(pollutionValue),
                            pm25 = AqicnDto.Data.Iaqi.Pm25(pollutionValue),
                            so2 = AqicnDto.Data.Iaqi.So2(pollutionValue)
                    )
            ))
    private val pollutionDto = aqicnDto.toPollutionDto()

    @Test
    fun shouldProperlyCreatePollutionDto() {
        Assertions.assertEquals(cityName, pollutionDto.city)
        pollutionDto.aqis
                .forEach { Assertions.assertEquals(AirPollutionLevel.GOOD, it.value) }
    }

}