package com.zee.chattingapp.firebaseRep

import android.app.Activity
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


class AuthRepo(private val application: Application) {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser:MutableLiveData<FirebaseUser> = MutableLiveData()
    private var sCode = ""

    fun register(code: String){
        val credential = PhoneAuthProvider.getCredential(sCode,code)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener {
                task ->
                    if(task.isSuccessful)
                        firebaseUser.postValue(firebaseAuth.currentUser)
                    else Toast.makeText(application,"Registration failed "+task.exception.toString(),Toast.LENGTH_LONG).show()

                }
    }


    fun login(phoneNo: String){

        val mCallbacks = object : OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                credential.smsCode?.let { register(it) }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(application, "$e",Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: ForceResendingToken
            ) {
                sCode = verificationId
            }
        }


        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+91$phoneNo") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            //.setActivity(context) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

    }


    fun logout(){
        firebaseAuth.signOut()
    }


   
}