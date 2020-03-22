package com.example.happyroutine

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_user_information.*

class user_information : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)
    }

    fun goToUserExercise(view: View) {
        val intent = Intent(this, routine::class.java)
        startActivity(intent)
    }

}
