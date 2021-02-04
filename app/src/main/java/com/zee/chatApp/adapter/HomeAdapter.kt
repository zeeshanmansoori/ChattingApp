package com.zee.chatApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.zee.chatApp.R
import com.zee.chatApp.model.Chat

class HomeAdapter(options: FirestoreRecyclerOptions<Chat>) :
    FirestoreRecyclerAdapter<Chat, HomeAdapter.ChatHolder>(
        options
    ) {

    companion object {
        const val LEFT_LAYOUT = 3324
        const val RIGHT_LAYOUT = 1284
    }

    val firebaseAuth = FirebaseAuth.getInstance()

    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textview = itemView.findViewById<TextView>(R.id.chat_container)
        private val username = itemView.findViewById<TextView>(R.id.user_name)

        fun bind(currentItem: Chat) {
            textview.text = currentItem.msg
            username.text = currentItem.name

        }
    }

    override fun getItemViewType(position: Int): Int {

//        return if (getItem(position).userName==firebaseAuth.currentUser?.displayName)
//            RIGHT_LAYOUT
//        else LEFT_LAYOUT
        return if (getItem(position).name == "zee")  RIGHT_LAYOUT else LEFT_LAYOUT

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {

        val view = if (viewType == LEFT_LAYOUT) LayoutInflater.from(parent.context)
            .inflate(R.layout.single_chat_layout_left, parent, false)
        else LayoutInflater.from(parent.context)
            .inflate(R.layout.single_chat_layout_right, parent, false)
        return ChatHolder(view)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int, model: Chat) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

    }


}