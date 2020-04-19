package com.example.happyroutine.ui.activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.happyroutine.R

class exercise : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
    }

    fun goToSettings(view: View) {
       // val intent = Intent(this, settings::class.java)
        //startActivity(intent)
    }
}