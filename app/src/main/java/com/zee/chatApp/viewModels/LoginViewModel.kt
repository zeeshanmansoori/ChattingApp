package com.zee.chatApp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.zee.chatApp.model.AuthRepo

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepo:AuthRepo = AuthRepo(application)
    private val firebaseUser = authRepo.firebaseUser

//
//    fun sendVerificationCode(phoneNo:String?,activity:Activity){
//        authRepo.login(phoneNo!!,activity)
//    }

    fun verifyCode(sysCode:String,code:String){
        authRepo.register(sysCode,code)
    }

    fun getUser(): MutableLiveData<FirebaseUser> = firebaseUser


}