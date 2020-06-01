package com.example.happyroutine.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.happyroutine.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings


class MainActivity : AppCompatActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()

        FirebaseFirestore.getInstance().firestoreSettings = settings
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