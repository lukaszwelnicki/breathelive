package com.software.lukaszwelnicki.breathelive.aqicnclient.namespace

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "aqicn")
class AqicnNamespace {
    lateinit var scheme: String
    lateinit var host: String
    lateinit var path: String
    lateinit var token: String
}
