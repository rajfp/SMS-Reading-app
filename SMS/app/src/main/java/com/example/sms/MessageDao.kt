package com.example.sms

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("SELECT * FROM message")
    suspend fun getAll(): List<Message>

    @Insert
    suspend fun insertMessage(message: Message) : Long


}