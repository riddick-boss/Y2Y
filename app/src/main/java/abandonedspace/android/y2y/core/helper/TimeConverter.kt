package abandonedspace.android.y2y.core.helper

import abandonedspace.android.y2y.domain.helper.ITimeConverter
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class TimeConverter @Inject constructor() : ITimeConverter {

    private val zoneId: ZoneId by lazy {
        try {
            ZoneId.systemDefault()
        } catch (e: Exception) {
            ZoneId.of("UTC")
        }
    }

    override fun millisToZonedDateTime(millis: Long): ZonedDateTime =
        ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), zoneId)

    override fun zonedDateTimeToMillis(zonedDateTime: ZonedDateTime): Long =
        zonedDateTime.toInstant().toEpochMilli()

    override fun dayMonthYearToZonedDateTime(day: Int, month: Int, year: Int): ZonedDateTime =
        ZonedDateTime.of(year, month, day, 0, 0,  0, 0, zoneId)
}