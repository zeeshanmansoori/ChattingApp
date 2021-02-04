package com.zee.chatApp.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import java.util.*

data class Chat(val name: String ="", val msg:String = "",
                val date: Timestamp = Timestamp(Date()))

data class fUser(
    val name: String = "",
    val phoneNo: String = "", var image: String = "",
    val bio: String = ""
)
