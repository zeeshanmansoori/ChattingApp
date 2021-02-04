package com.zee.chatApp.model

data class Chat(val name: String ="", val msg:String = "",val date:String="")

data class User(
    val name: String = "",
    val phoneNo: String = "", var image: String = "",
    val bio: String = ""
)
