package com.software.lukaszwelnicki.breathelive.service

import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.toPollutionDto
import com.software.lukaszwelnicki.breathelive.aqicnclient.service.AqicnRequestsService
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import org.springframework.stereotype.Service

@Service
class AirPollutionLevelService(private val aqicnRequestsService: AqicnRequestsService) {

    val getPollutionByGeolocation = { geo: Geolocation -> aqicnRequestsService.pollutionByGeolocation(geo).map { it.toPollutionDto() } }
    val getPollutionByCity = { city: String -> aqicnRequestsService.pollutionByCity(city).map { it.toPollutionDto() } }

}

