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
import com.example.happyroutine.ui.activity.ExpandableAdapter
import com.example.happyroutine.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_user_information.*
import kotlinx.android.synthetic.main.fragment_social_join_platform.*
import kotlinx.android.synthetic.main.fragment_social_messages.*
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDrawerToggle = ActionBarDrawerToggle(activity, drawer_layout, social_toolbar, R.string.open, R.string.close)
        mDrawerToggle!!.isDrawerIndicatorEnabled = true
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        social_toolbar.title = "HappyRoutine Messenger"
        social_toolbar.setTitleTextColor(Color.WHITE)
        mDrawerToggle!!.syncState()

        expList = exp_list

        setChildren()
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
                                        }
                                        //Add the user to the corresponding group based on what it offers
                                        if (otherOffers[i] == "Diet Advice") {
                                            dietList.add(username)
                                        }
                                        if (otherOffers[i] == "Exercise advice") {
                                            trainerList.add(username)
                                        }
                                        if (otherOffers[i] == "Company") {
                                            companyList.add(username)
                                        }
                                    }
                                }
                            }
                        }
                        //Sort every list so the users appear alphabetically
                        recommendedList.sort()
                        dietList.sort()
                        trainerList.sort()
                        companyList.sort()

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

                            /*
                             * Per obtenir el username, accedeix a l'array bidimensional amb:
                             * childList[groupPosition][childPosition]
                             * on groupPosition és el bloc on es troba l'usuari (dieta, entrenament...)
                             * i childPosition la posició de l'usuari en aquest bloc.
                            */
                            Log.i("user", childList[groupPosition][childPosition])
                            //Prova per iniciar una activity al fer click sobre un usuari qualsevol.
                            //val intent = Intent(activity, MainActivity::class.java)
                            //drawer_layout.closeDrawer(Gravity.START)
                            //startActivity(intent)
                            false
                        }
                    }
            }
    }
}
