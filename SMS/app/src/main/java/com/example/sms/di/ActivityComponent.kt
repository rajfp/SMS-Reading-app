package com.example.sms.di

import com.example.sms.db.ActivityModule
import com.example.sms.activity.MainActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {
    fun inject(activity: MainActivity)
}