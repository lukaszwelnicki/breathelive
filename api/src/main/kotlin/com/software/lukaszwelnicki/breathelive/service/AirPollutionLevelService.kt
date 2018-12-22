package com.software.lukaszwelnicki.breathelive.service

import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.PollutionDto
import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.toPollutionDto
import com.software.lukaszwelnicki.breathelive.aqicnclient.service.AqicnRequestsService
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AirPollutionLevelService(private val aqicnRequestsService: AqicnRequestsService) {

    fun getPollutionByGeolocation(geo: Geolocation): Mono<PollutionDto> =
            geo.let(aqicnRequestsService::getMeasurementsByGeo)
                    .map { it.toPollutionDto() }

    fun getPollutionByCity(city: String): Mono<PollutionDto> =
            aqicnRequestsService.getMeasurementsByCity(city)
                    .map { it.toPollutionDto() }
}

