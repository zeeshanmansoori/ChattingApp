package com.zee.chatApp.view.verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.zee.chatApp.R
import com.zee.chatApp.databinding.ActivityVerificationBinding
import com.zee.chatApp.model.fUser
import com.zee.chatApp.view.home.HomeActivity
import com.zee.chatApp.view.profile.USER_REF

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    private var mAuth = FirebaseAuth.getInstance()
    private lateinit var firestore: FirebaseFirestore
    private var otp: String? = null
    private val TAG = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_verification)
        firestore = FirebaseFirestore.getInstance()
        val verificationCode = intent.getStringExtra("String")
        binding.phoneNo = intent.getStringExtra("phoneNumber")
        Log.d(
                TAG,
        "Phone number in otpverification acitibity is ${binding.phoneNo}"
        )

        binding.verifyBtn.setOnClickListener {
            otp = binding.otpTextView.text.toString()
            if (otp != null && otp!!.length == 6) {
                verifyPhoneNumberWithCode(verificationCode, otp)
            }
        }
    }


    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String?) {
        // [START verify_with_code]
        if (verificationId !=null && code !=null)
        {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            // [END verify_with_code]
            signInWithPhoneAuthCredential(credential)
        }


    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    Toast.makeText(
                        this,
                        "Authentication successful",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    addToDb()
                } else {
                    // Sign in failed
                    Log.d(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        this,
                        "signInWithCredential:failure",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        binding.otpTextView.error = "Invalid code."
                    }
                }
            }
    }

    private fun addToDb() {

        firestore.collection(USER_REF).document(binding.phoneNo.toString()).set(
            fUser(name = intent.getStringExtra("name").toString(),phoneNo = binding.phoneNo.toString())
        ).addOnSuccessListener {
            Intent(this,HomeActivity::class.java).also {
                startActivity(it)
            }
        }
    }


}