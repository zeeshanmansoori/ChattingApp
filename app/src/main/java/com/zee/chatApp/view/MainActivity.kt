package com.zee.chatApp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.zee.chatApp.R
import com.zee.chatApp.databinding.ActivityMainBinding
import com.zee.chatApp.view.verification.VerificationActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val TAG = "TAG"
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        binding.login.setOnClickListener { view: View? ->
            var phoneNumber = binding.phoneNo.text.toString().trim { it <= ' ' }
            phoneNumber = "+91$phoneNumber"
            Log.d(TAG, "OnButtonClicked:  phone number$phoneNumber")
            sendCode(phoneNumber)
        }
    }

    private fun sendCode(phone: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val user = FirebaseAuth.getInstance().currentUser
                Log.d("TAG", "onVerificationCompleted")

                //Prefs.put(getApplicationContext(), user);
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
                Log.d("TAG", "onVerificationFailed")
            }

            override fun onCodeSent(s: String, token: PhoneAuthProvider.ForceResendingToken) {
                Log.d("TAG", "onCodeSent")
                val intent = Intent(this@MainActivity, VerificationActivity::class.java)
                intent.putExtra("String", s)
                intent.putExtra("Token", token)
                intent.putExtra("phoneNumber", binding.phoneNo.text.toString().trim { it <= ' ' })
                startActivity(intent)
            }
        }


}