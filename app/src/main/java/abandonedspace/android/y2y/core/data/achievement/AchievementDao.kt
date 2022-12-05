package abandonedspace.android.y2y.core.data.achievement

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {

    @Insert
    suspend fun insert(roomAchievement: RoomAchievement)

    @Query("SELECT * FROM achievements_table ORDER BY timestamp_millis")
    fun getAchievements(): Flow<List<RoomAchievement>>
}