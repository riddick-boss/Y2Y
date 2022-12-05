package abandonedspace.android.y2y.domain.repository.achievements

import abandonedspace.android.y2y.domain.model.Achievement
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface AchievementsRepository {

    fun getAchievements(): Flow<List<Achievement>>

    suspend fun insert(
        name: String,
        description: String?,
        categoryId: Int?,
        prideLevel: Int,
        timeStamp: ZonedDateTime
    )
}