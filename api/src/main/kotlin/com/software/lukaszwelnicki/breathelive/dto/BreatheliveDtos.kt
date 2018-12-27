package com.software.lukaszwelnicki.breathelive.dto

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "breathelive")
class BreatheliveProperties {
    private lateinit var samplingInSeconds: String
    fun samplingInSeconds(): Long = samplingInSeconds.toLong()
}