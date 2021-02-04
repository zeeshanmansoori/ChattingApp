package com.zee.chatApp.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.zee.chatApp.R
import com.zee.chatApp.adapter.HomeAdapter
import com.zee.chatApp.databinding.ActivityHomeBinding
import com.zee.chatApp.model.CHAT
import com.zee.chatApp.model.Chat
import com.zee.chatApp.view.profile.ProfileActivity
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()


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
}