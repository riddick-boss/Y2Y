package abandonedspace.android.y2y.domain.helper

import java.time.ZonedDateTime

interface ITimeConverter {

    fun millisToZonedDateTime(millis: Long): ZonedDateTime

    fun zonedDateTimeToMillis(zonedDateTime: ZonedDateTime): Long

    fun dayMonthYearToZonedDateTime(day: Int, month: Int, year: Int): ZonedDateTime
}