package com.example.sms.db

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sms.receiver.MessageReceiver
import com.example.sms.viewmodel.MessageViewModel
import com.example.sms.viewmodel.MessageViewModelProviderFactory
import com.example.sms.activity.MainActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: MainActivity) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity)

    @Provides
    fun provideMessageViewModel(
        app: Application,
        messageRepository: MessageRepository
    ): MessageViewModel = ViewModelProvider(
        activity,
        MessageViewModelProviderFactory(
            app,
            messageRepository
        )
    ).get(MessageViewModel::class.java)


    @Provides
    fun provideMessageReceiver(): MessageReceiver =
        MessageReceiver()
}