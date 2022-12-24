package com.learning.assignment.core.di

import com.learning.data.core.dao.ClassifiedListingDao
import com.learning.data.core.impl.ClassifiedListingRepositoryImpl
import com.learning.data.core.network.NetworkDataSource
import com.learning.domain.repository.ClassifiedListingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideClassifiedListingRepository(
        dao: ClassifiedListingDao
    ): ClassifiedListingRepository =
        ClassifiedListingRepositoryImpl(dao)

    @Provides
    @Singleton
    internal fun provideClassifiedListingDao(
        dataSource: NetworkDataSource
    ): ClassifiedListingDao =
        ClassifiedListingDao(dataSource)
}
