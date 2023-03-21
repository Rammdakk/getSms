package com.rammdakk.getSms.ui.view.mainScreen

import com.rammdakk.getSms.data.model.Service

interface ChatListClickListener {
    fun onChatListItemClick(task: Service?)
}