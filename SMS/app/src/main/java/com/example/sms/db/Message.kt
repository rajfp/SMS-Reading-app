package com.example.sms.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    //@ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "message") val message: String?,
    @ColumnInfo(name = "time") val time: LocalDateTime?
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass)
            return false
        other as Message
        if (uid != other.uid)
            return false
        if (message != other.message)
            return false
        if (time != other.time)
            return false
        return true
    }
}
