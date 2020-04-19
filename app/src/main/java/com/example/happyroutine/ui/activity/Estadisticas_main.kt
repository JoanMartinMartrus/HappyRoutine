package com.example.happyroutine.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.happyroutine.R


class Estadisticas_main : AppCompatActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas_main)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    /*fun goToWeight(view: View) {
        val intent = Intent(this, Estadisticas_weight::class.java)
        startActivity(intent)
    }
    fun goToTrainningDays(view: View) {
        val intent = Intent(this, Estadisticas_trainning_days::class.java)
        startActivity(intent)
    }*/
}

