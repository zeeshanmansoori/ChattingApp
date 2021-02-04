package com.zee.chatApp.model

data class NotificationData(
    val title:String,
    val message:String
)


data class PushNotification(
    val data: NotificationData,
    val to: String
)