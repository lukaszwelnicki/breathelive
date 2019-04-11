package com.software.lukaszwelnicki.breathelive.email

import com.software.lukaszwelnicki.breathelive.clients.pollution.PollutionDto
import com.software.lukaszwelnicki.breathelive.domain.AirPollutionLevel
import com.software.lukaszwelnicki.breathelive.domain.Geolocation
import com.software.lukaszwelnicki.breathelive.domain.User
import org.springframework.boot.autoconfigure.mail.MailProperties
import org.springframework.core.io.ResourceLoader
import org.springframework.mail.javamail.JavaMailSender
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.LocalTime

class EmailServiceImplTest extends Specification {

    public static final String EMAIL = "name.surname@mail.com"
    public static final String FIRSTNAME = "Name"
    public static final String LASTNAME = "Surname"
    public static final String CITY = "Warsaw"
    def javaMailSender = Mock(JavaMailSender)
    def mailProperties = Mock(MailProperties)
    def resourceLoader = Mock(ResourceLoader)
    def user = new User(
            "1",
            EMAIL,
            FIRSTNAME,
            LASTNAME,
            new Geolocation(20.0, 20.0),
            true,
            new HashSet<LocalTime>())
    Map pollutionMap = ["pm25": AirPollutionLevel.MODERATE,
                        "pm10": AirPollutionLevel.GOOD,
                        "so2" : AirPollutionLevel.GOOD]
    def pollutionDto = new PollutionDto(LocalDateTime.now(), CITY, pollutionMap)
    def emailDto = new EmailDto(user, pollutionDto)

    EmailService classUnderTest = new EmailServiceImpl(javaMailSender, mailProperties, resourceLoader)

    def "should prepare text message correctly"() {
        given:
            def message = prepareTextMessage()
            println 'spock ' +message
            println 'kotlin ' + classUnderTest.prepareTextMessage(user, pollutionDto)
        expect:
            classUnderTest.prepareTextMessage(user, pollutionDto) == message
    }

    private String prepareTextMessage() {
        def mailMessage = """Greetings, ${FIRSTNAME}!
        |
        |Here is the air pollution level report for ${CITY}
        |PM25: ${pollutionMap.pm25}
        |PM10: ${pollutionMap.pm10}
        |SO2: ${pollutionMap.so2}
        |
        |To get more information about air quality indices see the table below:<br>
        """.stripMargin().trim().replace(System.getProperty("line.separator"), "<br>")
        return mailMessage
    }

}
