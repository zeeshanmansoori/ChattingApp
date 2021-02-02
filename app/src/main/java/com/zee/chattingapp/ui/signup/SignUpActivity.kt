package com.zee.chattingapp.ui.signup

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class SignUpActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Enables Always-on
        setAmbientEnabled()
    }
}