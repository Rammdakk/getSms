package com.rammdakk.getSms.ui.view.serviceScreen

import com.rammdakk.getSms.core.model.Service

interface ChatListClickListener {
    fun onChatListItemClick(service: Service?)
}