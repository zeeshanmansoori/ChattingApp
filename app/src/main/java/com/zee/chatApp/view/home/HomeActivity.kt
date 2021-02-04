package com.zee.chatApp.view.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.zee.chatApp.R
import com.zee.chatApp.adapter.HomeAdapter
import com.zee.chatApp.databinding.ActivityHomeBinding
import com.zee.chatApp.model.*
import com.zee.chatApp.service.FirebaseService
import com.zee.chatApp.view.profile.ProfileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

const val TOPIC = "/chats/chat"
class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private val TAG = "TAG"
    private lateinit var token:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token
            token = it.token
            Log.d("TAG","${it.token}")
        }

        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        val query = firestore.collection(CHAT).orderBy("date",Query.Direction.DESCENDING)

        val firestoreRecyclerOptions = FirestoreRecyclerOptions.Builder<Chat>()
            .setQuery(query,Chat::class.java)
            .build()

        homeAdapter = HomeAdapter(firestoreRecyclerOptions)

        with(binding){

            homeRecyclerView.apply {
                setItemViewCacheSize(30)
                val manager = LinearLayoutManager(applicationContext)
                manager.stackFromEnd = true
                manager.reverseLayout = true
                layoutManager = manager
                adapter = homeAdapter
            }

            sendBtn.setOnClickListener {
                val text = enterText.text.toString()
                if (text.isNotEmpty())
                    sendChat(text)
            }
        }

    }


    private fun sendChat(text: String) {
        //firebaseAuth.currentUser!!.displayName.toString()
        val chat = Chat("zee",text,
            Timestamp(Date())
            )

        binding.enterText.text.clear()
        firestore.collection(CHAT)
            .add(chat)
            .addOnSuccessListener {
                val title = "Notification from ${firebaseAuth.currentUser}"
                val message = chat.msg

                if(title.isNotEmpty() && message.isNotEmpty() && token.isNotEmpty()){
                    PushNotification(
                        NotificationData(title,message),
                        TOPIC
                    )
                        .also {
                            sendNotification(it)
                        }
                }

            }

    }

    override fun onStart() {
        super.onStart()
        homeAdapter.startListening()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Intent(this,ProfileActivity::class.java).also {
            startActivity(it)
        }
        return super.onOptionsItemSelected(item)

    }


    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
        }
    }
}