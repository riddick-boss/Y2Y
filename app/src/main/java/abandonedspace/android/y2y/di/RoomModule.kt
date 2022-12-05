package abandonedspace.android.y2y.di

import abandonedspace.android.y2y.core.data.achievement.AchievementDao
import abandonedspace.android.y2y.core.data.categories.room.CategoryDao
import abandonedspace.android.y2y.core.data.room.AchievementsDatabase
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AchievementsDatabase = Room.databaseBuilder(context, AchievementsDatabase::class.java, "DatabseName").build()

    @Provides
    @Singleton
    fun provideCategoryDao(db: AchievementsDatabase): CategoryDao = db.categoryDao()

    @Provides
    @Singleton
    fun provideAchievementDao(db: AchievementsDatabase): AchievementDao = db.achievementDao()
}