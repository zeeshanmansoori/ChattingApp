package com.zee.chatApp.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.zee.chatApp.R
import com.zee.chatApp.databinding.ActivityOtpVerificationBinding
import com.zee.chatApp.viewModels.LoginViewModel
import java.util.concurrent.TimeUnit

class OtpVerificationActivity : AppCompatActivity() {

    private  val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityOtpVerificationBinding
    private var sysCode = ""
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_otp_verification)
        binding.phoneNo = intent.getStringExtra("phoneNo")

        loginViewModel.getUser().observe(this, Observer {
            user ->
            if (user!=null)
            {
                Toast.makeText(this,"account created successfully",Toast.LENGTH_SHORT).show()
                Log.d("TAG","account created successfully")

            }

            Log.d("TAG","observer added")
        })

        //loginViewModel.sendVerificationCode(binding.phoneNo,this)
        sendVerificationCode(binding.phoneNo.toString())

        binding.verifyBtn.setOnClickListener {
            Log.d("TAG","btn clicled")
            val code = binding.otpTextView.toString()
            if(code.isNotEmpty() && code.length==6)
            {
                Log.d("TAG","btn clicled maza aaya")
                loginViewModel.verifyCode(sysCode,code)
            }

        }


    }

    private fun sendVerificationCode(phoneNo:String) {
        Log.d("TAG","login called")
        val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("fuser",credential.smsCode.toString())
                Toast.makeText(application,"onVerificationCompleted",Toast.LENGTH_SHORT).show()
                credential.smsCode?.let { loginViewModel.verifyCode(it,it) }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("fuser",e.toString())
                Toast.makeText(application, "onVerificationFailed",Toast.LENGTH_LONG).show()

            }

            override fun onCodeSent(
                    verificationId: String,

                    token: PhoneAuthProvider.ForceResendingToken
            ) {
                sysCode = verificationId
                Toast.makeText(application,"onCodeSent $verificationId",Toast.LENGTH_SHORT).show()

            }
        }



        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber("+91$phoneNo") // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)
                .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
                .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

    }

}