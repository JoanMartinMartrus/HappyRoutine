package com.example.happyroutine
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

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