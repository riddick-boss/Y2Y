package abandonedspace.android.y2y.core.repository.achievements

import abandonedspace.android.y2y.core.data.achievement.RoomAchievement
import abandonedspace.android.y2y.domain.helper.ITimeConverter
import abandonedspace.android.y2y.domain.model.Achievement
import java.time.ZonedDateTime
import javax.inject.Inject

class Mapper @Inject constructor(
    private val timeConverter: ITimeConverter
) {
    fun toDomainAchievement(roomAchievement: RoomAchievement): Achievement =
        roomAchievement.let {
            Achievement(
                id = it.id!!,
                name = it.name,
                description = it.description,
                categoryId = it.categoryId,
                prideLevel = it.prideLevel,
                timeStamp = timeConverter.millisToZonedDateTime(it.timestampMillis)
            )
        }

    fun toAchievement(
        name: String,
        description: String?,
        categoryId: Int?,
        prideLevel: Int,
        timeStamp: ZonedDateTime
    ): RoomAchievement =
        RoomAchievement(
            timestampMillis = timeConverter.zonedDateTimeToMillis(timeStamp),
            categoryId = categoryId,
            prideLevel = prideLevel,
            name = name,
            description = description
        )
}