package abandonedspace.android.y2y.core.repository.achievements

import abandonedspace.android.y2y.core.data.achievements.room.Achievement
import abandonedspace.android.y2y.domain.model.Date
import java.time.Month
import java.time.Year
import javax.inject.Inject

class AchievementMapper @Inject constructor() {
    fun toDomainAchievement(roomAchievement: Achievement): abandonedspace.android.y2y.domain.model.Achievement =
        abandonedspace.android.y2y.domain.model.Achievement(
            id = roomAchievement.id,
            title = roomAchievement.title,
            description = roomAchievement.description,
            date = Date(month = Month.of(roomAchievement.month), year = Year.of(roomAchievement.year))
        )
}