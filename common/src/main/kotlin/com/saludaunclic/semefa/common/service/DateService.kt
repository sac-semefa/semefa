package com.saludaunclic.semefa.common.service

import com.saludaunclic.semefa.common.config.DateProperties
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone
import javax.annotation.PostConstruct

@Service
class DateService(private val dateProperties: DateProperties) {
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HHmmss")

    private lateinit var timeZone: TimeZone

    @PostConstruct
    fun init() {
        timeZone = TimeZone.getTimeZone(ZoneId.of(dateProperties.timeZone))
        System.setProperty("user.timezone", timeZone.id)
        TimeZone.setDefault(timeZone)
    }

    fun now(): LocalDateTime = LocalDateTime.now()

    fun toDate(dateTime: LocalDateTime): Date = Date.from(dateTime.atZone(timeZone.toZoneId()).toInstant())

    fun toTimestamp(dateTime: LocalDateTime): Timestamp = Timestamp(toDate(dateTime).time)

    fun nowTimestamp(): Timestamp = toTimestamp(now())

    fun formatDate(dateTime: LocalDateTime): String = dateFormatter.format(dateTime)

    fun formatTime(dateTime: LocalDateTime): String  = timeFormatter.format(dateTime)
}
