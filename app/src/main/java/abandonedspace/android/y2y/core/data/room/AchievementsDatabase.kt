package abandonedspace.android.y2y.core.data.room

import abandonedspace.android.y2y.core.data.achievements.room.Achievement
import abandonedspace.android.y2y.core.data.achievements.room.AchievementDao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Achievement::class], version = 1)
abstract class AchievementsDatabase : RoomDatabase() {
    abstract fun achievementDao(): AchievementDao
}