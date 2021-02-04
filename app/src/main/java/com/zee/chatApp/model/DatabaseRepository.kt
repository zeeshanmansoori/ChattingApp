package com.zee.chatApp.model

import com.google.firebase.firestore.FirebaseFirestore

const val CHAT="Chat"
class DatabaseRepository {
    private val firebaseFirestore = FirebaseFirestore.getInstance()


    fun putChat(chat: Chat){
        firebaseFirestore.collection(CHAT).add(chat).addOnCompleteListener {
            if (it.isSuccessful)
            {}
            else{}
        }
    }

    fun getChat(){
        firebaseFirestore.collection(CHAT).get().addOnSuccessListener {
            val chats =  it.toObjects(Chat::class.java)
        }
    }
}