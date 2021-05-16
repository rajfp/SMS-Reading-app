package com.example.sms.di

import android.app.Application
import com.example.sms.db.AppDatabase
import com.example.sms.db.MessageRepository
import com.example.sms.MyApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: MyApplication)

    fun getApplication(): Application

    fun getDatabaseService(): AppDatabase

    fun getUserRepository(): MessageRepository

}