package com.zee.chattingapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.zee.chattingapp.firebaseRep.AuthRepo

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepo:AuthRepo = AuthRepo(application)

    fun sendVerificationCode(phoneNo:String?){
        authRepo.login(phoneNo!!)
    }

    fun verifyCode(code:String){
        authRepo.register(code)
    }

    fun getUser(): MutableLiveData<FirebaseUser> = authRepo.firebaseUser

}