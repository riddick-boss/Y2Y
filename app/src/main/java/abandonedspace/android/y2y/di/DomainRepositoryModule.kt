package abandonedspace.android.y2y.di

import abandonedspace.android.y2y.core.repository.categories.CategoriesRepositoryImpl
import abandonedspace.android.y2y.domain.repository.categories.CategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainRepositoryModule {

    @Binds
    abstract fun provideCategoriesRepository(impl: CategoriesRepositoryImpl): CategoriesRepository
}