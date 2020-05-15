package com.example.happyroutine.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.happyroutine.R
import com.example.happyroutine.ui.activity.ChatLogActivity
import com.example.happyroutine.ui.activity.ChatMessage
import com.example.happyroutine.ui.activity.ExpandableAdapter
import com.example.happyroutine.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.active_chats_row.*
import kotlinx.android.synthetic.main.active_chats_row.view.*
import kotlinx.android.synthetic.main.active_chats_row.view.textView_latest_messages_username
import kotlinx.android.synthetic.main.activity_user_information.*
import kotlinx.android.synthetic.main.fragment_diet.*
import kotlinx.android.synthetic.main.fragment_social_join_platform.*
import kotlinx.android.synthetic.main.fragment_social_messages.*
import kotlinx.android.synthetic.main.user_row_new_messages.*
import kotlinx.android.synthetic.main.user_row_new_messages.view.*
import kotlinx.android.synthetic.main.user_row_new_messages.view.username_textview_new_message_drawer
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.zip.Inflater

class SocialFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance().collection("users")
    private val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private var expandableAdapter: ExpandableAdapter? = null
    private var expList: ExpandableListView? = null
    private val parents = arrayOf("Recommended", "Diet Advice", "Training Advice", "Company")
    private var mDrawerToggle : ActionBarDrawerToggle? = null
    private var recommendedList = ArrayList<String>()
    private var dietList = ArrayList<String>()
    private var trainerList = ArrayList<String>()
    private var companyList = ArrayList<String>()
    private var recommendedPairList = ArrayList<Pair<String, String>>()
    private var dietPairList = ArrayList<Pair<String, String>>()
    private var trainerPairList = ArrayList<Pair<String, String>>()
    private var companyPairList = ArrayList<Pair<String, String>>()
    private var root: View? = null
    private val adapter = GroupAdapter<ViewHolder>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_social_messages, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDrawerToggle = ActionBarDrawerToggle(activity, drawer_layout, social_toolbar, R.string.open, R.string.close)
        mDrawerToggle!!.isDrawerIndicatorEnabled = true
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        social_toolbar.title = "HappyRoutine Messenger"
        social_toolbar.setTitleTextColor(Color.WHITE)
        mDrawerToggle!!.syncState()
        recyclerview_active_chats.adapter = adapter

        expList = exp_list
        listenForLatestMessages()
        setChildren()

        adapter.setOnItemClickListener { item, view ->
            val intent = Intent(activity, ChatLogActivity::class.java)
            val row = item as LatestMessagesRow
            intent.putExtra(USER_KEY1, view.textView_latest_messages_username.text)
            intent.putExtra(USER_KEY2, row.userId)
            startActivity(intent)
        }
    }
    companion object {
        val USER_KEY1 = "username"
        val USER_KEY2 = "id"
    }

    private val latestMessagesMap = HashMap<String, LatestMessagesRow>()

    private fun refreshRecyclerView(){
        adapter.clear()
        latestMessagesMap.forEach {
            adapter.add(it.value)
        }
    }

    private fun listenForLatestMessages(){
        val fromId = FirebaseAuth.getInstance().currentUser?.uid
        val ref = FirebaseFirestore.getInstance().collection("/latest-messages/$fromId/$fromId")

        ref.addSnapshotListener { value, e ->
            for(doc in value!!){
                val toId = doc.id
                val ref2 = FirebaseFirestore.getInstance().collection("/latest-messages/$fromId/$fromId/$toId/$toId")

                ref2.addSnapshotListener { messageValue, ex ->
                    val messagesList = ArrayList<Pair<Pair<String, String>, Pair<String, Long>>>()
                    for(message in messageValue!!){
                        messagesList.add(Pair(Pair(message.getString("fromId"), message.getString("toId")),
                            Pair(message.getString("text"), message.getLong("timestamp"))) as Pair<Pair<String, String>, Pair<String, Long>>)
                    }
                    messagesList.sortBy { it.second.second }
                    db.document(toId)
                        .get().addOnCompleteListener { task->
                            if(task.isSuccessful){
                                val document = task.result
                                if (document!!.exists()) {
                                    val username = document.get("name").toString().plus(" ")
                                        .plus(document.get("surname").toString())
                                    val id = toId
                                    val text = messagesList[messagesList.lastIndex].second.first
                                    val format = SimpleDateFormat("HH:mm")
                                    val hour = format.format(Date(messagesList[messagesList.lastIndex].second.second))

                                    latestMessagesMap[toId] = LatestMessagesRow(id, username, text, hour)
                                    refreshRecyclerView()
                                }
                            }
                    }
                }
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun setChildren() {
        var username: String
        var otherOffers: ArrayList<String>
        var currentNeeds = ArrayList<String>()

        db.document(uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        currentNeeds = document["needs"] as ArrayList<String>
                        username_textview_new_message.text = document.get("name").toString().plus(" ")
                            .plus(document.get("surname").toString())
                        email_textview_new_message.text = document.get("email").toString()
                    }
                }

                db.get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            //Get username and offers from other users
                            if (document.id != uid) {
                                otherOffers = document.get("offer") as ArrayList<String>
                                username = document.get("name").toString().plus(" ")
                                    .plus(document.get("surname").toString())

                                //Check if other user has the same offer as the current user's need
                                for (need in currentNeeds) {
                                    for (i in 0 until otherOffers.size) {
                                        //If so add it to "Recommended", showing which need is covered
                                        if (otherOffers[i] == need) {
                                            recommendedList.add(username
                                                .plus(" (").plus(need).plus(")"))
                                            recommendedPairList.add(
                                                Pair(username
                                                    .plus(" (").plus(need).plus(")"), document.id)
                                            )
                                        }
                                        //Add the user to the corresponding group based on what it offers
                                        if (otherOffers[i] == "Diet Advice") {
                                            dietList.add(username)
                                            dietPairList.add(Pair(username, document.id))
                                        }
                                        if (otherOffers[i] == "Exercise advice") {
                                            trainerList.add(username)
                                            trainerPairList.add(Pair(username, document.id))
                                        }
                                        if (otherOffers[i] == "Company") {
                                            companyList.add(username)
                                            companyPairList.add(Pair(username, document.id))
                                        }
                                    }
                                }
                            }
                        }
                        //Sort every list so the users appear alphabetically
                        recommendedList.sort()
                        recommendedPairList.sortBy { it.first }
                        dietList.sort()
                        dietPairList.sortBy { it.first }
                        trainerList.sort()
                        trainerPairList.sortBy { it.first }
                        companyList.sort()
                        companyPairList.sortBy { it.first }

                        //Add all lists to ExpandableListView
                        val childList = ArrayList<ArrayList<String>>()
                        childList.add(recommendedList)
                        childList.add(dietList)
                        childList.add(trainerList)
                        childList.add(companyList)
                        expandableAdapter = this.context?.let { ExpandableAdapter(it, childList, parents) }
                        expList!!.setAdapter(expandableAdapter)

                        //Listener to start a new chat when selecting user
                        expList!!.setOnChildClickListener {
                                parent, v, groupPosition, childPosition, id ->

                            val intent = Intent(activity, ChatLogActivity::class.java)
                            var usernameChatLog = childList[groupPosition][childPosition]

                            if(groupPosition == 0){
                                if(usernameChatLog.contains(" (Diet Advice)", ignoreCase = true)
                                    || usernameChatLog.contains(" (Training Advice)", ignoreCase = true)){
                                    println("IM IN")
                                    usernameChatLog = usernameChatLog.removeSuffix(" Advice)")
                                    usernameChatLog = usernameChatLog.plus(")")
                                }

                                intent.putExtra(USER_KEY2, recommendedPairList[childPosition].second)
                            }

                            if(groupPosition == 1){
                                usernameChatLog = usernameChatLog.plus(" (Diet)")
                                intent.putExtra(USER_KEY2, dietPairList[childPosition].second)
                            }

                            if(groupPosition == 2){
                                usernameChatLog = usernameChatLog.plus(" (Training)")
                                intent.putExtra(USER_KEY2, trainerPairList[childPosition].second)
                            }

                            if(groupPosition == 3){
                                usernameChatLog = usernameChatLog.plus(" (Company)")
                                intent.putExtra(USER_KEY2, companyPairList[childPosition].second)
                            }

                            intent.putExtra(USER_KEY1, usernameChatLog)

                            drawer_layout.closeDrawer(Gravity.START)
                            startActivity(intent)
                            false
                        }
                    }
            }
    }
}

class LatestMessagesRow(private val id: String, private val user: String, private val message: String,
                        private val hour: String) : Item<ViewHolder>(){
    val userId = id
    val latestHour = hour

    override fun getLayout(): Int {
        return R.layout.active_chats_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_latest_messages_username.text = user
        if(message.length < 20){
            viewHolder.itemView.textView_latest_messages_message.text = message.plus(" (at ")
                .plus(latestHour).plus(")")
        }
        else{
            viewHolder.itemView.textView_latest_messages_message.text = message.take(20)
                .plus("... (at ").plus(latestHour).plus(")")
        }

    }
}
