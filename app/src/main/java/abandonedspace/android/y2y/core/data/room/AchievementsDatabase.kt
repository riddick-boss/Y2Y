package abandonedspace.android.y2y.core.data.room

import abandonedspace.android.y2y.core.data.categories.room.Category
import abandonedspace.android.y2y.core.data.categories.room.CategoryDao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Category::class], version = 1)
abstract class AchievementsDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}