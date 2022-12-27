package abandonedspace.android.y2y.di

import abandonedspace.android.y2y.BuildConfig
import abandonedspace.android.y2y.core.data.achievements.room.AchievementDao
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
    ): AchievementsDatabase = Room.databaseBuilder(context, AchievementsDatabase::class.java, BuildConfig.ROOM_DB_NAME).build()

    @Provides
    @Singleton
    fun provideCategoryDao(db: AchievementsDatabase): AchievementDao = db.achievementDao()
}