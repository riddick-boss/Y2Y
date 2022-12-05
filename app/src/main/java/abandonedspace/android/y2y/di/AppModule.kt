package abandonedspace.android.y2y.di

import abandonedspace.android.y2y.core.helper.TimeConverter
import abandonedspace.android.y2y.domain.helper.ITimeConverter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideTimeConverter(impl: TimeConverter): ITimeConverter
}