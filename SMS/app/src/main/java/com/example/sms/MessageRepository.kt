package com.example.sms

class MessageRepository(val db: AppDatabase) {

     suspend fun getMessages(): ArrayList<Message> {
        return db.messageDao().getAll() as ArrayList<Message>
    }

    suspend fun addMessage(message: Message) =
        db.messageDao().insertMessage(message)

}