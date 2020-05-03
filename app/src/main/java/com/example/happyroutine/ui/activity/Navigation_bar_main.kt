package com.example.happyroutine.ui.activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.happyroutine.R
import com.example.happyroutine.ui.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Navigation_bar_main : AppCompatActivity()  {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_bar_main)
        showSelectedFragment(EditProfileFragment());
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val bottomNavigationBar: BottomNavigationView = findViewById(R.id.button_navigation_bar)

        bottomNavigationBar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.diet -> {
                    showSelectedFragment(DietFragment());
                    true
                }
                R.id.profile -> {
                   showSelectedFragment(EditProfileFragment());
                    true
                }
                R.id.exercises -> {
                    showSelectedFragment(TrainningFragment());
                    true
                }
                R.id.statistics -> {
                    showSelectedFragment(EstadisticasMainFragment());
                    true
                }
                R.id.social -> {
                    val db = FirebaseFirestore.getInstance().collection("users")
                    val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
                    var platformUser : Boolean? = false

                    db.document(uid).get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val document = task.result
                                if (document!!.exists()) {
                                    platformUser = document["participantPlatform"] as Boolean
                                }
                            }
                            if (platformUser!!) {
                                showSelectedFragment(SocialFragment());
                            }
                            else{
                                showSelectedFragment(SocialJoinPlatform());
                            }
                        }
                    true
                }
                else -> false
            }
        }
    }

    fun  showSelectedFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout_navigation_bar,fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}