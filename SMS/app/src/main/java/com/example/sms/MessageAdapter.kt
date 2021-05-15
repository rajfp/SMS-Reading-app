package com.example.sms

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_layout.view.*
import java.time.Duration
import java.time.LocalDateTime
import java.util.ArrayList

class MessageAdapter(private val context: Context, private val list: ArrayList<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MessageViewHolder(
        LayoutInflater.from(context)
            .inflate(R.layout.message_layout, parent, false)
    )


    override fun getItemCount() = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Duration.between(list[position].time, LocalDateTime.now()).toDays() > 0) {
                if (position != 0 && Duration.between(list[position].time, LocalDateTime.now())
                        .toDays() == Duration.between(list[position - 1].time, LocalDateTime.now())
                        .toDays()
                ) {
                    holder.itemView.tv_time.visibility = View.GONE
                } else {
                    holder.itemView.tv_time.text =
                        Duration.between(list[position].time, LocalDateTime.now()).toDays()
                            .toString() + " days ago"
                }
            } else {
                if (position != 0 && Duration.between(list[position].time, LocalDateTime.now())
                        .toMinutes() == Duration.between(list[position - 1].time, LocalDateTime.now())
                        .toMinutes()
                )
                    holder.itemView.tv_time.visibility = View.GONE
                else

                    holder.itemView.tv_time.text =
                        Duration.between(list[position].time, LocalDateTime.now()).toMinutes()
                            .toString() + " minutes ago"
            }
        }
        holder.itemView.tv_msg.text = list[position].message

    }
}