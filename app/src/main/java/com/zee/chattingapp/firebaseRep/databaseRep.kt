package com.zee.chattingapp.firebaseRep

import com.google.firebase.firestore.FirebaseFirestore

class databaseRep {
    lateinit var firestore: FirebaseFirestore



    constructor(){
        firestore = FirebaseFirestore.getInstance()
    }


}