package com.software.lukaszwelnicki.breathelive.web

import com.software.lukaszwelnicki.breathelive.domain.AirPollutionLevel
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import reactor.core.publisher.Mono
import java.time.LocalDateTime

data class PollutionDto(val date: LocalDateTime,
                        val city: String,
                        val airQualityIndicies: Map<String, AirPollutionLevel>)

interface PollutionService {

    fun getPollutionByCity(city: String) : Mono<PollutionDto>
    fun getPollutionByGeolocation(geo: Geolocation) : Mono<PollutionDto>

}
