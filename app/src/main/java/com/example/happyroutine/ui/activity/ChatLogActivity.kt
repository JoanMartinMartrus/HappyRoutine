package com.example.happyroutine.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.happyroutine.R
import com.example.happyroutine.ui.fragment.SocialFragment
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.messaging.FirebaseMessaging
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_new_date.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
import java.text.SimpleDateFormat


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
            if(editText_chat_log.text.trim().toString().isNotEmpty()){
                sendMessage()
            }

        }

        toolbar_chat_log.setNavigationOnClickListener{
            finish()
        }
    }

    private fun listenForMessages(){
        val fromId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val toId = intent.getStringExtra(SocialFragment.USER_KEY2) as String

        val ref = FirebaseFirestore.getInstance().collection("/user-messages/$fromId/$toId").orderBy("timestamp")
        var messagePosition = 0

        ref.addSnapshotListener { value, e ->
            adapter.clear()
            val messagesList = ArrayList<Pair<Pair<String, String>, Pair<String, Timestamp>>>()
            messagesList.clear()

            for (doc in value!!) {
                messagesList.add(
                    Pair(
                        Pair(doc.getString("fromId"), doc.getString("toId")),
                        Pair(doc.getString("text"), doc.get("timestamp"))
                    ) as Pair<Pair<String, String>, Pair<String, Timestamp>>
                )
            }

            if (messagesList.isNotEmpty() && messagesList[0].second.second != null) {
                val formatDate = SimpleDateFormat("dd MMMM yyyy")
                var currentDate = formatDate.format(messagesList[0].second.second.toDate())
                messagePosition += 1
                adapter.add(ChatNewDate(currentDate))

                for (message in messagesList) {
                    if (message.second.second != null) {
                        var newDate = formatDate.format(message.second.second.toDate())
                        if (newDate > currentDate) {
                            messagePosition += 1
                            adapter.add(ChatNewDate(newDate))
                            currentDate = newDate
                        }

                        val formatHour = SimpleDateFormat("HH:mm")
                        val hour = formatHour.format(message.second.second.toDate())
                        messagePosition += 1
                        if (message.first.first == FirebaseAuth.getInstance().uid) {
                            adapter.add(ChatToItem(message.second.first, hour))
                        }
                        else {
                            adapter.add(ChatFromItem(message.second.first, hour))
                        }
                    }
                    else {
                        listenForMessages()
                    }
                }
            }
            recyclerview_chat_log.adapter = adapter
            recyclerview_chat_log.smoothScrollToPosition(messagePosition)
        }

    }

    private fun sendMessage(){
        val text = editText_chat_log.text.toString()
        val fromId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val toId = intent.getStringExtra(SocialFragment.USER_KEY2) as String

        val reference = FirebaseFirestore.getInstance().collection("/user-messages/$fromId/$toId")
        val toReference = FirebaseFirestore.getInstance().collection("/user-messages/$toId/$fromId")
        val messageId = reference.document().id
        val chatMessage = ChatMessage(messageId, text, fromId, toId, FieldValue.serverTimestamp())

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

class ChatMessage(val id: String, val text: String, val fromId: String, val toId: String, val timestamp: FieldValue?){
    constructor() : this("", "", "", "", FieldValue.serverTimestamp())
}

class ChatFromItem(val text: String, val hour: String): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_chat_from_row.text = text
        viewHolder.itemView.textView_from_hour.text = hour
    }
}

class ChatToItem(val text: String, val hour: String): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_chat_to_row.text = text
        viewHolder.itemView.textView_to_hour.text = hour
    }
}

class ChatNewDate(val date: String): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_new_date
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_chat_date.text = date
    }
}
