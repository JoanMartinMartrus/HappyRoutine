package com.example.happyroutine.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.happyroutine.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_information.*
import kotlinx.android.synthetic.main.fragment_social_join_platform.*

class SocialJoinPlatform : Fragment() {

    private val db = FirebaseFirestore.getInstance().collection("users")
    private val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social_join_platform, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkBoxPlatformAnswerSocial.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                optionsLayout.visibility=View.VISIBLE
            }else {
                optionsLayout.visibility=View.INVISIBLE
            }
        }
        button_join_platform.setOnClickListener {
            val offer = getOffer()
            val needs = getNeeds()
            db.document(uid).update("participantPlatform", true)
            db.document(uid).update("offer", offer)
            db.document(uid).update("needs", needs)

            fragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout_navigation_bar, SocialFragment())?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()
        }
    }

    private fun getOffer(): List<String> {
        val offerList = mutableListOf<String>()
        if (offerCompanyCheckbox2.isChecked) {
            offerList.add(offerCompanyCheckbox2.text.toString())
        }
        if (offerDietAdviceCheckbox2.isChecked) {
            offerList.add(offerDietAdviceCheckbox2.text.toString())
        }
        if (offerExerciseAdviceCheckbox2.isChecked) {
            offerList.add(offerExerciseAdviceCheckbox2.text.toString())
        }
        return offerList
    }

    private fun getNeeds(): List<String> {
        val needsList = mutableListOf<String>()
        if (needsCompanyCheckbox2.isChecked) {
            needsList.add(needsCompanyCheckbox2.text.toString())
        }
        if (needsDietAdviceCheckbox2.isChecked) {
            needsList.add(needsDietAdviceCheckbox2.text.toString())
        }
        if (needsExerciseAdviceCheckbox2.isChecked) {
            needsList.add(needsExerciseAdviceCheckbox2.text.toString())
        }
        return needsList
    }
}