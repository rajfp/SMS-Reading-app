package com.example.sms.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sms.R
import com.example.sms.db.Message
import kotlinx.android.synthetic.main.message_layout.view.*
import java.time.Duration
import java.time.LocalDateTime
import java.util.ArrayList

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MessageViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.message_layout, parent, false)
    )


    override fun getItemCount() = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<Message>() {

        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Duration.between(differ.currentList[position].time, LocalDateTime.now()).toDays() > 0) {
                if (position > 0 && Duration.between(differ.currentList[position].time, LocalDateTime.now())
                        .toDays() == Duration.between(differ.currentList[position - 1].time, LocalDateTime.now())
                        .toDays()
                ) {
                    holder.itemView.tv_time.visibility = View.GONE
                } else {
                    holder.itemView.tv_time.visibility = View.VISIBLE
                    holder.itemView.tv_time.text =
                        Duration.between(differ.currentList[position].time, LocalDateTime.now()).toDays()
                            .toString() + " days ago"
                }
            } else {
                if (position > 0 && Duration.between(differ.currentList[position].time, LocalDateTime.now())
                        .toHours() == Duration.between(differ.currentList[position - 1].time, LocalDateTime.now())
                        .toHours()
                )
                    holder.itemView.tv_time.visibility = View.GONE
                else {
                    holder.itemView.tv_time.visibility = View.VISIBLE
                    holder.itemView.tv_time.text =
                        Duration.between(differ.currentList[position].time, LocalDateTime.now())
                            .toHours()
                            .toString() + " hours ago"
                }
            }
        }
        holder.itemView.tv_msg.text = differ.currentList[position].message

    }

}