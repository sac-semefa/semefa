package com.saludaunclic.semefa.regafi.service.date

import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone

class DefaultDateService(val timeZone: TimeZone): DateService {
    companion object {
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HHmmss")
    }

    init {
        System.setProperty("user.timezone", this.timeZone.id);
        TimeZone.setDefault(this.timeZone);
    }

    override fun now(): LocalDateTime = LocalDateTime.now()

    override fun toDate(dateTime: LocalDateTime): Date = Date.from(dateTime.atZone(timeZone.toZoneId()).toInstant())

    override fun toTimestamp(dateTime: LocalDateTime): Timestamp = Timestamp(toDate(dateTime).time)

    override fun nowTimestamp(): Timestamp = toTimestamp(now())

    override fun formatDate(dateTime: LocalDateTime): String = dateFormatter.format(dateTime)

    override fun formatTime(dateTime: LocalDateTime): String  = timeFormatter.format(dateTime)
}
