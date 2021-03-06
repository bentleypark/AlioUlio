package com.alio.ulio.di

import com.alio.ulio.data.NetworkDataSource
import com.alio.ulio.domain.AlarmUseCase
import com.alio.ulio.domain.UploadUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @ViewModelScoped
    @Provides
    fun provideAlarmUseCase(networkDataSource: NetworkDataSource): AlarmUseCase {
        return AlarmUseCase(networkDataSource)
    }

    @ViewModelScoped
    @Provides
    fun provideUploadUseCase(networkDataSource: NetworkDataSource): UploadUseCase {
        return UploadUseCase(networkDataSource)
    }
}