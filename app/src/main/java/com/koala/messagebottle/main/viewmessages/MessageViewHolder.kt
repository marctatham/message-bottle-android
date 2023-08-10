package com.koala.messagebottle.main.viewmessages

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koala.messagebottle.R
import com.koala.messagebottle.common.messages.domain.MessageEntity


class MessageViewHolder(testView: View) : RecyclerView.ViewHolder(testView) {

    private var textView: TextView = testView.findViewById(R.id.txtMessage)

    fun bind(messageEntity: MessageEntity) {
        textView.text = messageEntity.message
    }

}