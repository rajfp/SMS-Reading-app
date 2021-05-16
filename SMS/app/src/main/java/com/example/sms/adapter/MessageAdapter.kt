package com.example.sms.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private var list: ArrayList<Message> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MessageViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.message_layout, parent, false)
    )


    override fun getItemCount() = list.size

    fun submitList(messageList: ArrayList<Message>) {
        val oldList = list
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            MessageItemDiffCallback(
                oldList,
                messageList
            )
        )
        list = messageList
        diffResult.dispatchUpdatesTo(this)
    }

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
                        .toHours() == Duration.between(list[position - 1].time, LocalDateTime.now())
                        .toHours()
                )
                    holder.itemView.tv_time.visibility = View.GONE
                else

                    holder.itemView.tv_time.text =
                        Duration.between(list[position].time, LocalDateTime.now()).toHours()
                            .toString() + " hours ago"
            }
        }
        holder.itemView.tv_msg.text = list[position].message

    }

    class MessageItemDiffCallback(
        var oldList: ArrayList<Message>,
        var newList: ArrayList<Message>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].uid == newList[newItemPosition].uid
        }

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition))
        }

    }
}