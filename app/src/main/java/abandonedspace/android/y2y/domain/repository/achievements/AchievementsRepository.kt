package abandonedspace.android.y2y.domain.repository.achievements

import abandonedspace.android.y2y.domain.model.Achievement
import kotlinx.coroutines.flow.Flow
import java.time.Month
import java.time.Year

interface AchievementsRepository {

    suspend fun insert(title: String, description: String?, month: Month, year: Year)

    suspend fun delete(id: Int)

    fun getAchievements(): Flow<List<Achievement>>
}