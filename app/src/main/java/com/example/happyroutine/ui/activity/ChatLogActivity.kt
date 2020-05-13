package com.example.happyroutine.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.happyroutine.R
import com.example.happyroutine.ui.fragment.SocialFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLogActivity : AppCompatActivity() {

    private val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        val otherUserUsername = intent.getStringExtra(SocialFragment.USER_KEY1)
        setSupportActionBar(toolbar_chat_log)
        supportActionBar?.title = otherUserUsername
        toolbar_chat_log.setTitleTextColor(Color.WHITE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listenForMessages()
        button_send_chat_log.setOnClickListener {
            sendMessage()
        }
    }

    private fun listenForMessages(){
        val fromId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val toId = intent.getStringExtra(SocialFragment.USER_KEY2) as String
        val ref = FirebaseFirestore.getInstance().collection("/user-messages/$fromId/$toId")

        ref.addSnapshotListener { value, e ->
            adapter.clear()
            val messagesList = ArrayList<Pair<Pair<String, String>, Pair<String, Long>>>()

            for(doc in value!!) {
                messagesList.add(Pair(Pair(doc.getString("fromId"), doc.getString("toId")),
                    Pair(doc.getString("text"), doc.getLong("timestamp"))) as Pair<Pair<String, String>, Pair<String, Long>>)
            }

            messagesList.sortBy { it.second.second }
            for(message in messagesList){
                if(message.first.first == FirebaseAuth.getInstance().uid){
                   adapter.add(ChatToItem(message.second.first))
                }
                else{
                    adapter.add(ChatFromItem(message.second.first))
                }
            }
            recyclerview_chat_log.adapter = adapter
        }

    }

    private fun sendMessage(){
        val text = editText_chat_log.text.toString()
        val fromId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val toId = intent.getStringExtra(SocialFragment.USER_KEY2) as String

        val reference = FirebaseFirestore.getInstance().collection("/user-messages/$fromId/$toId")
        val toReference = FirebaseFirestore.getInstance().collection("/user-messages/$toId/$fromId")
        val messageId = reference.document().id
        val chatMessage = ChatMessage(messageId, text, fromId, toId, System.currentTimeMillis() / 1000)

        reference.document(messageId).set(chatMessage).addOnSuccessListener {
            Log.d("Send Message:", "Message $messageId saved")
        }
        toReference.document(messageId).set(chatMessage).addOnSuccessListener {
            Log.d("Send Message:", "Message $messageId saved")
        }
        editText_chat_log.text.clear()

        val data = hashMapOf("message" to "message")
        FirebaseFirestore.getInstance().document("/latest-messages/$fromId/$fromId/$toId").set(data)
        FirebaseFirestore.getInstance().document("/latest-messages/$toId/$toId/$fromId").set(data)

        val latestMessageRef = FirebaseFirestore.getInstance().collection("/latest-messages/$fromId/$fromId/$toId/$toId")
        latestMessageRef.document(messageId).set(chatMessage)
        val latestMessageToRef = FirebaseFirestore.getInstance().collection("/latest-messages/$toId/$toId/$fromId/$fromId")
        latestMessageToRef.document(messageId).set(chatMessage)


    }
}


class ChatMessage(val id: String, val text: String, val fromId: String, val toId: String, val timestamp: Long){
    constructor() : this("", "", "", "", -1)
}

class ChatFromItem(val text: String): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_chat_from_row.text = text
    }
}

class ChatToItem(val text: String): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_chat_to_row.text = text
    }
}
