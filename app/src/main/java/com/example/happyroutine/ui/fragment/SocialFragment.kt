package com.example.happyroutine.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.happyroutine.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class SocialFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance().collection("users")
    private val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val name = FirebaseAuth.getInstance().currentUser?.displayName.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val test = ArrayList<String>()
        test.add("Amic 1")
        test.add("Amic 2")
        val test2 = ArrayList<String>()
        test.add("Amic 3")
        test.add("Amic 4")
        val adapter = this.context?.let { ArrayAdapter(it,android.R.layout.simple_list_item_1, test) }
        recommended_list.adapter = adapter
        val adapter2 = this.context?.let { ArrayAdapter(it,android.R.layout.simple_list_item_2, test2) }
        diet_list.adapter = adapter2*/
        //displayUsers()
        getCurrentUserInfo()

        /*button_newChat.setOnClickListener {
            fragmentManager?.let {
                it.beginTransaction().replace(R.id.frame_layout_navigation_bar, SocialNewChats())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }*/
    }

    private fun getCurrentUserInfo(){
        var currentUserName: String? = ""
        var surname: String? = ""
        var currentUserEmail: String? = ""
        var needsCurrentUser = ArrayList<String>()
        db.document(uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        currentUserName = document["name"] as String
                        surname = document["surname"] as String
                        currentUserEmail = document["email"] as String
                        needsCurrentUser = document["needs"] as ArrayList<String>
                    }
                }
                currentUserName = currentUserName.plus(" ").plus(surname)

                println(currentUserName)
                println(currentUserEmail)
                for(need in needsCurrentUser){
                    println(need)
                }
            getUsers(uid)
            }
    }

    private fun getUsers(current: String){

        var listOfUsers = ArrayList<String>()
        db.document().get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    //if (document!!.exists()) {
                    listOfUsers = document as ArrayList<String>
                    //}
                }
                for(user in listOfUsers){
                    println(user)
                }
            }
    }

    /*private fun displayUsers(){
        val name = FirebaseAuth.getInstance().currentUser?.displayName
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                val adapter = GroupAdapter<ViewHolder>()

                p0?.children?.forEach{
                    val user = it.getValue(UserData::class.java)
                    if(user != null){
                        adapter.add(UserItem(user))
                    }
                }
                recyclerview_newmessage.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })

    }*/
}/*
class UserItem(val user: UserData): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.username_textview_new_message.text = user.username
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_messages
    }
}

class currentUser(val username: String, val email: String, val needs: ArrayList<String>()){
    constructor() : this("", "", "")
}*/
