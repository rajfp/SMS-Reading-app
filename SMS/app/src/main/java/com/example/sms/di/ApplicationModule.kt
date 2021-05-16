package com.example.sms.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.sms.db.AppDatabase
import com.example.sms.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MyApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideContext(): Context = application


    @Provides
    @Singleton
    fun provideDatabaseService(): AppDatabase =
        Room.databaseBuilder(
            application, AppDatabase::class.java,
            "message_db"
        ).build()

}