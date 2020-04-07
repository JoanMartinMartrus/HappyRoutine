package com.example.happyroutine.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.happyroutine.R

class settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun goToUserInformation(view: View) {
        val intent = Intent(this, user_information::class.java)
        startActivity(intent)
    }

}
