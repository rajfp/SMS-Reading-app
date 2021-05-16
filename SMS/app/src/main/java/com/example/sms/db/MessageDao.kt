package com.example.sms.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sms.db.Message

@Dao
interface MessageDao {

    @Query("SELECT * FROM message")
    suspend fun getAll(): List<Message>

    @Insert
    suspend fun insertMessage(message: Message) : Long


}