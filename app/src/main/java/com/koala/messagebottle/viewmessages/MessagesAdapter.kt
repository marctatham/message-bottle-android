package com.koala.messagebottle.viewmessages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koala.messagebottle.R
import com.koala.messagebottle.common.messages.domain.MessageEntity


class MessagesAdapter(

    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<MessageViewHolder>() {

    var messages: List<MessageEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view: View = layoutInflater.inflate(R.layout.message_recycler_view, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val messageEntity = messages[position]
        holder.bind(messageEntity)
    }

    override fun getItemCount(): Int = messages.size

}
