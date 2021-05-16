package com.example.sms.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sms.db.MessageRepository
import javax.inject.Singleton

@Singleton
class MessageViewModelProviderFactory(val app: Application, val newsRepository: MessageRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MessageViewModel(app, newsRepository) as T
    }
}
