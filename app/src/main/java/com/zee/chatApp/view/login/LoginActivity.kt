package com.zee.chatApp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.zee.chatApp.R
import com.zee.chatApp.databinding.ActivityLoginBinding
import com.zee.chatApp.view.home.HomeActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth.currentUser?.let {
            Intent(this,HomeActivity::class.java)
                .also {
                    startActivity(it)
                }
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.login.setOnClickListener {
            moveToOtpVerification()
        }

    }

    private fun moveToOtpVerification() {

        Intent(this,OtpVerificationActivity::class.java)
                .apply {
                    putExtra("phoneNo",binding.phoneNo.text.toString())
                    putExtra("name",binding.name.text.toString())
                }
                .also {
                    startActivity(it)
                }
    }

}