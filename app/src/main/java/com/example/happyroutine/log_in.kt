package com.example.happyroutine
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class log_in : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
    }

    fun goToSignUp(view: View) {
        val intent = Intent(this, sign_up::class.java)
        startActivity(intent)
    }

    fun goToForgotPassword(view: View) {
        val intent = Intent(this, forgot_my_password::class.java)
        startActivity(intent)
    }
}
