package com.software.lukaszwelnicki.breathelive

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = Initializer)
abstract class TestcontainersConfig extends Specification {

    @Shared
    static GenericContainer mongoContainer = new GenericContainer("mongo:latest").withExposedPorts(27017)

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            mongoContainer.start()
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.data.mongodb.host=" + mongoContainer.getContainerIpAddress(),
                    "spring.data.mongodb.port=" + mongoContainer.getMappedPort(27017)
            )
            values.applyTo(configurableApplicationContext)
        }
    }

}

