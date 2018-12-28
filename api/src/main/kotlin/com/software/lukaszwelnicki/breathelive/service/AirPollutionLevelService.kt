package com.software.lukaszwelnicki.breathelive.service

import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.toPollutionDto
import com.software.lukaszwelnicki.breathelive.aqicnclient.service.AqicnRequestsService
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import org.springframework.stereotype.Service

@Service
class AirPollutionLevelService(private val aqicnRequestsService: AqicnRequestsService) {

    fun getPollutionByGeolocation(geo: Geolocation) = aqicnRequestsService.getPollutionByGeolocation(geo).map { it.toPollutionDto() }
    fun getPollutionByCity(city: String) = aqicnRequestsService.getPollutionByCity(city).map { it.toPollutionDto() }

}

