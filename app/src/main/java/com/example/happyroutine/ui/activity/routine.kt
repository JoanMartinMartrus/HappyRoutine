package com.example.happyroutine.ui.activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.happyroutine.R

class routine : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine)

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