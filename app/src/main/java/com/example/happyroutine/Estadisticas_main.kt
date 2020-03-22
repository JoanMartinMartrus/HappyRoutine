package com.example.happyroutine

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class Estadisticas_main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas_main)
    }
    fun goToWeight(view: View) {
        val intent = Intent(this, Estadisticas_weight::class.java)
        startActivity(intent)
    }
    fun goToTrainningDays(view: View) {
        val intent = Intent(this, Estadisticas_trainning_days::class.java)
        startActivity(intent)
    }
}

