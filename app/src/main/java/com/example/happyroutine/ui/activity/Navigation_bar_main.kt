package com.example.happyroutine.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.happyroutine.R
import com.example.happyroutine.ui.fragment.DietFragment
import com.example.happyroutine.ui.fragment.EditProfileFragment
import com.example.happyroutine.ui.fragment.ExerciseFragment
import com.example.happyroutine.ui.fragment.SocialFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Navigation_bar_main : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_bar_main)
        showSelectedFragment(EditProfileFragment());
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
                    showSelectedFragment(ExerciseFragment());
                    true
                }
                R.id.statistics -> {
                    showSelectedFragment(EstadisticasMainFragment());
                    true
                }
                R.id.social -> {
                    showSelectedFragment(SocialFragment());
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