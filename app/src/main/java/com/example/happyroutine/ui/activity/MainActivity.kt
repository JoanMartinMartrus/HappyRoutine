package com.example.happyroutine.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.happyroutine.R

class MainActivity : AppCompatActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun goToLogIn(view: View) {
        val intent = Intent(this, log_in::class.java)
        startActivity(intent)
    }
    fun goToSignUp(view: View) {
        val intent = Intent(this, sign_up::class.java)
        startActivity(intent)
    }

}
