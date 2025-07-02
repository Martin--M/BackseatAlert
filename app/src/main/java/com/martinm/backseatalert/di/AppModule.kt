package com.martinm.backseatalert.di

import android.content.Context
import com.martinm.backseatalert.data.recognition.ActivityRecognitionManager
import com.martinm.backseatalert.data.repository.SettingsRepository
import com.martinm.backseatalert.data.repository.SettingsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepositoryImpl(context)
    }

    @Provides
    fun provideActivityRecognitionManager(@ApplicationContext context: Context): ActivityRecognitionManager {
        return ActivityRecognitionManager(context)
    }
}