package com.example.sms

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MessageViewModelProviderFactory(val app: Application, val newsRepository: MessageRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MessageViewModel(app,newsRepository) as T
    }
}
