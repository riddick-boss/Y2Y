package abandonedspace.android.y2y.core.repository.achievements

import abandonedspace.android.y2y.core.data.achievement.AchievementDao
import abandonedspace.android.y2y.domain.model.Achievement
import abandonedspace.android.y2y.domain.repository.achievements.AchievementsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import javax.inject.Inject

class RoomAchievementsRepository @Inject constructor(
    private val dao: AchievementDao,
    private val mapper: Mapper,
) : AchievementsRepository {

    override fun getAchievements(): Flow<List<Achievement>> = dao.getAchievements().map {
        it.map { achievement -> mapper.toDomainAchievement(achievement) }.sorted()
    }

    override suspend fun insert(
        name: String,
        description: String?,
        categoryId: Int?,
        prideLevel: Int,
        timeStamp: ZonedDateTime
    ) {
        if (name.isBlank()) throw IllegalArgumentException("RoomAchievement name can not be null!")

        val achievement = mapper.toAchievement(
            name = name,
            description = description,
            categoryId = categoryId,
            prideLevel = prideLevel,
            timeStamp = timeStamp
        )

        dao.insert(achievement)
    }
}