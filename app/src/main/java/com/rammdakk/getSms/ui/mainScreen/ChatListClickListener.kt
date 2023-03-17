package com.rammdakk.getSms.ui.mainScreen

import com.rammdakk.getSms.data.model.Service

interface ChatListClickListener {
    fun onChatListItemClick(task: Service?)
}