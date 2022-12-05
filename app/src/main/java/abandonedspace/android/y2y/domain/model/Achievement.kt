package abandonedspace.android.y2y.domain.model

import java.time.ZonedDateTime

data class Achievement(
    val id: Int,
    val name: String,
    val description: String?,
    val categoryId: Int?,
    val prideLevel: Int,
    val timeStamp: ZonedDateTime
) : Comparable<Achievement> {

    override fun compareTo(other: Achievement): Int = timeStamp.compareTo(other.timeStamp)
}
