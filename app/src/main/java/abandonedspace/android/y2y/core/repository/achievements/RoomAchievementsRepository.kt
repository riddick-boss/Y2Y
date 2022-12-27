package abandonedspace.android.y2y.core.repository.achievements

import abandonedspace.android.y2y.core.data.achievements.room.AchievementDao
import abandonedspace.android.y2y.domain.model.Achievement
import abandonedspace.android.y2y.domain.repository.achievements.AchievementsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.time.Month
import java.time.Year
import javax.inject.Inject

class RoomAchievementsRepository @Inject constructor(
    private val mapper: AchievementMapper,
    private val dao: AchievementDao
) : AchievementsRepository {
    override suspend fun insert(title: String, description: String?, month: Month, year: Year) {
        if (title.isBlank()) throw IllegalArgumentException("Achievement title cannot be null!")

        val achievement = abandonedspace.android.y2y.core.data.achievements.room.Achievement(
            title = title,
            description = description,
            month = month.value,
            year = year.value
        )

        dao.insert(achievement)
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }

    override fun getAchievements(): Flow<List<Achievement>> = dao.getAchievements().map { list ->
        list.map { achievement -> mapper.toDomainAchievement(achievement) }
    }.distinctUntilChanged()
}