package com.zee.chatApp.model

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.*


class AuthRepo(private val application: Application) {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser:MutableLiveData<FirebaseUser> = MutableLiveData()
    private var sCode = ""

    fun register(sysCode:String,code: String){
        Log.d("TAG","reg called")
        val credential = PhoneAuthProvider.getCredential(sysCode,code)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener {
                task ->
                    if(task.isSuccessful)
                        firebaseUser.postValue(firebaseAuth.currentUser)
                    else Toast.makeText(application,"Registration failed "+task.exception.toString(),Toast.LENGTH_LONG).show()


                }.addOnFailureListener {
                    Toast.makeText(application,it.toString(),Toast.LENGTH_LONG).show()
                }
                .addOnSuccessListener{
                    Toast.makeText(application,it.toString(),Toast.LENGTH_LONG).show()
                }



    }


//    fun login(phoneNo: String,activity: Activity){
//
//        Log.d("TAG","login called")
//        val mCallbacks = object : OnVerificationStateChangedCallbacks() {
//
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                Log.d("fuser",credential.smsCode.toString())
//                Toast.makeText(application,"onVerificationCompleted",Toast.LENGTH_SHORT).show()
//                credential.smsCode?.let { register(it) }
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                Log.d("fuser",e.toString())
//                Toast.makeText(application, "onVerificationFailed",Toast.LENGTH_LONG).show()
//
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//
//                token: ForceResendingToken
//            ) {
//                sCode = verificationId
//                Toast.makeText(application,"onCodeSent",Toast.LENGTH_SHORT).show()
//
//            }
//        }



//        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
//            .setPhoneNumber("+91$phoneNo") // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(activity)
//            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
//            .build()
//
//        PhoneAuthProvider.verifyPhoneNumber(options)
//
//    }


    fun logout(){
        firebaseAuth.signOut()
    }


   
}