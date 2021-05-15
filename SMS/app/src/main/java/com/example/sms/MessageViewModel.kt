package com.example.sms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MessageViewModel(
    val app: Application,
    val messageRepository: MessageRepository
) : AndroidViewModel(app) {

     val messageData: MutableLiveData<ArrayList<Message>> = MutableLiveData()


    fun fetchMessages() {
        viewModelScope.launch {
            messageData.value = messageRepository.getMessages()
        }
    }


    fun saveMessage(message: Message) =
        viewModelScope.launch {
            messageRepository.addMessage(message)
        }

}