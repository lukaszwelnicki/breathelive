package com.software.lukaszwelnicki.breathelive.service

import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.PollutionDto
import com.software.lukaszwelnicki.breathelive.domain.User
import com.software.lukaszwelnicki.breathelive.dto.EmailDto
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.mail.MailProperties
import org.springframework.core.io.ResourceLoader
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets


interface EmailService {
    fun sendPollutionEmail(emailData: EmailDto)
}

@Service
class EmailServiceImpl(private val javaMailSender: JavaMailSender,
                       private val mailProperties: MailProperties,
                       private val resourceLoader: ResourceLoader) : EmailService {

    private val logger = KotlinLogging.logger {}

    override fun sendPollutionEmail(emailData: EmailDto)  {
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name())
        helper.addAttachment("aqicn.png", resourceLoader.getResource("classpath:images/aqicn.png"))
        val inlineImage = "<img src=\"cid:aqicn.png\"></img><br/>"
        helper.setText(prepareTextMessage(emailData.user, emailData.pollutionDto) + inlineImage, true)
        helper.setSubject("Air pollution in ${emailData.pollutionDto.city}")
        helper.setTo(emailData.user.email)
        helper.setFrom(mailProperties.username)
        javaMailSender.send(message)
        logger.info("Email sent to: ${emailData.user.email}")
    }

    private fun prepareTextMessage(user: User, pollutionData: PollutionDto): String {
        return """
            |Greetings${user.firstName?.let { ", $it!" } ?: "!"}
            |
            |Here is the air pollution level report for ${pollutionData.city}
            |PM25: ${pollutionData.aqis.get("pm25")}
            |PM10: ${pollutionData.aqis.get("pm10")}
            |SO2: ${pollutionData.aqis.get("so2")}
            |
            |To get more information about air quality indices see the table below:
            |
            """.trimMargin().replace("\n", "<br>")
    }

}