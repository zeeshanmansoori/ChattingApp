package com.zee.chattingapp.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer import com.zee.chattingapp.R
import com.zee.chattingapp.databinding.ActivityOtpVerificationBinding
import com.zee.chattingapp.viewModels.LoginViewModel

class OtpVerificationActivity : AppCompatActivity() {

    private  val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityOtpVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_otp_verification)
        binding.phoneNo = intent.getStringExtra("phoneNo")

        loginViewModel.getUser().observe(this, Observer {
            user ->
            if (user!=null)
                Toast.makeText(this,"account created successfully",Toast.LENGTH_SHORT).show()

        })

        loginViewModel.sendVerificationCode(binding.phoneNo)

        binding.verifyBtn.setOnClickListener {
            val code = binding.otpTextView.toString()
            if(code.isNotEmpty() && code.length==6)
                loginViewModel.verifyCode(code)
        }


    }

}