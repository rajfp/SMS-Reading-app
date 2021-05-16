package com.example.sms.db

import com.example.sms.db.AppDatabase
import com.example.sms.db.Message
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(val db: AppDatabase) {

     suspend fun getMessages(): ArrayList<Message> {
        return db.messageDao().getAll() as ArrayList<Message>
    }

    suspend fun addMessage(message: Message) =
        db.messageDao().insertMessage(message)

}