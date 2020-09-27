package com.koala.messagebottle.viewmessages

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koala.messagebottle.R
import com.koala.messagebottle.common.messages.domain.MessageEntity


class MessageViewHolder(val testView: View) : RecyclerView.ViewHolder(testView) {

    lateinit var test: TextView

    init {
        test = testView.findViewById(R.id.txtMessage)
    }


    fun bind(messageEntity: MessageEntity) {
        test.text = messageEntity.message
    }

}