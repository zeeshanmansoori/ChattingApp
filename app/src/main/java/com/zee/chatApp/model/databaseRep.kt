package com.zee.chatApp.model

import com.google.firebase.firestore.FirebaseFirestore

class databaseRep {
    lateinit var firestore: FirebaseFirestore



    constructor(){
        firestore = FirebaseFirestore.getInstance()
    }


}