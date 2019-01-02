package com.software.lukaszwelnicki.breathelive.dto

import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.PollutionDto
import com.software.lukaszwelnicki.breathelive.domain.User
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "breathelive")
class BreatheliveProperties {
    lateinit var samplingInSeconds: String
    fun samplingInSecondsAsLong(): Long = samplingInSeconds.toLong()
    fun samplingInMinutesAsLong(): Long = samplingInSeconds.toLong() / 60
}

data class EmailDto(val user: User, val pollutionDto: PollutionDto)