package com.example.sms

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime

class MainActivity : AppCompatActivity(), MessageListener {

    private val messageReceiver = MessageReceiver()
    private var msgList = ArrayList<Message>()
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageViewModel: MessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        messageReceiver.bindListener(this)
        val repository = MessageRepository(AppDatabase(this))
        val viewModelProviderFactory = MessageViewModelProviderFactory(application, repository)
        messageViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(MessageViewModel::class.java)
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
        messageAdapter = MessageAdapter(this, list)
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
    override fun getMessage(message: String) {
        msgList.add(Message(message = message, time = LocalDateTime.now()))
        messageViewModel.saveMessage(Message(message = message, time = LocalDateTime.now()))
        messageAdapter.notifyDataSetChanged()
    }
}