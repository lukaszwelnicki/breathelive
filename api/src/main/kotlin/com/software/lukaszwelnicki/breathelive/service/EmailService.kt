package com.software.lukaszwelnicki.breathelive.service

import com.software.lukaszwelnicki.breathelive.aqicnclient.dto.PollutionDto
import com.software.lukaszwelnicki.breathelive.domain.User
import org.springframework.boot.autoconfigure.mail.MailProperties
import org.springframework.core.io.ClassPathResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets


interface EmailService {
    fun sendPollutionEmail(pollutionData: Pair<User, PollutionDto>)
}

@Service
class EmailServiceImpl(private val javaMailSender: JavaMailSender, private val mailProperties: MailProperties) : EmailService {

    override fun sendPollutionEmail(pollutionData: Pair<User, PollutionDto>) {
        sendEmail(pollutionData)
    }

    private fun sendEmail(pollutionData: Pair<User, PollutionDto>) {
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name())
        helper.addAttachment("aqicn.png", ClassPathResource("aqicn.png"))
        val inlineImage = "<img src=\"cid:aqicn.png\"></img><br/>"
        helper.setText(prepareTextMessage(pollutionData.first, pollutionData.second) + inlineImage, true)
        helper.setSubject("Air pollution in ${pollutionData.second.city}")
        helper.setTo(pollutionData.first.email)
        helper.setFrom(mailProperties.username)
        javaMailSender.send(message)
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
            """.trimMargin()
    }

}