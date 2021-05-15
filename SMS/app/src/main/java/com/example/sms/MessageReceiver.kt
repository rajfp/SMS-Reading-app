package com.example.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony


class MessageReceiver : BroadcastReceiver() {

    var messageListener: MessageListener? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                messageListener?.getMessage(sms.displayMessageBody)
            }

        }

    }

    fun bindListener(messageListener: MessageListener) {
        this.messageListener = messageListener
    }
}