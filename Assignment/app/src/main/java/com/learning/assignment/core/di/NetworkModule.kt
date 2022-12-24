package com.learning.assignment.core.di

import com.learning.data.core.Constants.BASE_URL
import com.learning.data.core.network.NetworkDataSource
import com.learning.data.core.network.RestApiBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideRetrofit() =
        RestApiBuilder(BASE_URL).build()

    @Provides
    @Singleton
    internal fun provideNetworkDataSource(retrofit: Retrofit): NetworkDataSource =
        NetworkDataSource(retrofit)
}
