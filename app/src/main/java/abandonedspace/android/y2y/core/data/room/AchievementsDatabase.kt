package abandonedspace.android.y2y.core.data.room

import abandonedspace.android.y2y.core.data.achievement.RoomAchievement
import abandonedspace.android.y2y.core.data.achievement.AchievementDao
import abandonedspace.android.y2y.core.data.categories.room.RoomCategory
import abandonedspace.android.y2y.core.data.categories.room.CategoryDao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomCategory::class, RoomAchievement::class], version = 1)
abstract class AchievementsDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    abstract fun achievementDao(): AchievementDao
}