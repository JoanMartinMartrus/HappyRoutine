package com.example.happyroutine
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView

class routine : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine)

        val bottomNavigationBar:BottomNavigationView = findViewById(R.id.navigation)

        bottomNavigationBar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.exercise_bottom_bar_item -> {
                    val intent = Intent(this, exercise::class.java)
                    startActivity(intent)
                    true
                }
                R.id.diet_bottom_bar_item -> {
                    val intent = Intent(this, diet::class.java)
                    startActivity(intent)
                    true
                }
                R.id.statistics_bottom_bar_item -> {
                    val intent = Intent(this, Estadisticas_main::class.java)
                    startActivity(intent)
                    true
                }
                R.id.social_bottom_bar_item -> {
                    val intent = Intent(this, social::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }

    fun goToExercise(view: View) {
        val intent = Intent(this, exercise::class.java)
        startActivity(intent)
    }

    fun goToSettings(view: View) {
        val intent = Intent(this, settings::class.java)
        startActivity(intent)
    }



}