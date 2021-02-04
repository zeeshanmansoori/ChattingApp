package com.zee.chatApp.view.verification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.zee.chatApp.R
import com.zee.chatApp.databinding.ActivityVerificationBinding

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    private var mAuth = FirebaseAuth.getInstance()
    private var otp: String? = null
    private val TAG = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_verification)
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
                        "signInWithCredential:success",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    val user = task.result!!.user
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


}