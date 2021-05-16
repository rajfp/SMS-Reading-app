package com.example.sms.activity

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sms.*
import com.example.sms.adapter.MessageAdapter
import com.example.sms.db.ActivityModule
import com.example.sms.db.Message
import com.example.sms.di.DaggerActivityComponent
import com.example.sms.listener.MessageListener
import com.example.sms.receiver.MessageReceiver
import com.example.sms.viewmodel.MessageViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
    MessageListener {

    @Inject
    lateinit var messageReceiver: MessageReceiver

    private var msgList = ArrayList<Message>()

    private lateinit var messageAdapter: MessageAdapter

    @Inject
    lateinit var messageViewModel: MessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .applicationComponent((application as MyApplication).applicationComponent).build()
            .inject(this)
        messageReceiver.bindListener(this)
        messageViewModel.fetchMessages()
        setObservers()
    }

    fun setObservers() {
        messageViewModel.messageData.observe(this, Observer {
            setRecyclerView(it)
        })
    }

    fun setRecyclerView(list: ArrayList<Message>) {
        msgList = list
        messageAdapter = MessageAdapter()
        messageAdapter.differ.submitList(msgList.toList())
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recycler_view.adapter = messageAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter().apply {
            addAction("android.provider.Telephony.SMS_RECEIVED")
        }
        registerReceiver(messageReceiver, intentFilter)
    }


    fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS), 1);
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else
                    finish()

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent?.getStringExtra("body")?.let {
            updateData(it)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getMessage(message: String) {
        updateData(message)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateData(message: String) {
        msgList.add(
            Message(
                message = message,
                time = LocalDateTime.now()
            )
        )

        messageViewModel.saveMessage(
            Message(
                message = message,
                time = LocalDateTime.now()
            )
        )
        messageAdapter.differ.submitList(msgList.toList())
    }
}