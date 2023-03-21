package com.rammdakk.getSms.ui.view.serviceScreen

import com.rammdakk.getSms.data.model.Service

interface ChatListClickListener {
    fun onChatListItemClick(task: Service?)
}