package abandonedspace.android.y2y.core.data.achievements.room

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {

    @WorkerThread
    @Insert
    suspend fun insert(achievement: Achievement)

    @WorkerThread
    @Query("DELETE FROM achievements_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM achievements_table ORDER BY year ASC, month ASC")
    fun getAchievements(): Flow<List<Achievement>>
}