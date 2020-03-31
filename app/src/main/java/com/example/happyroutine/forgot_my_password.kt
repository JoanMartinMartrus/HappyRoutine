package com.example.happyroutine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_forgot_my_password.*
import kotlinx.android.synthetic.main.activity_log_in.*

class forgot_my_password : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_my_password)
    }

    fun sendResetPasswordEmail(view: View) {
        if (editText.text.toString().isEmpty()) {
            editText.error = "Please, enter your e-mail"
            editText.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editText.text.toString()).matches()) {
            editText.error = "Please, enter a valid e-mail"
            editText.requestFocus()
            return
        }

        auth.sendPasswordResetEmail(editText.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "E-mail sent", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun goTologIn(view: View) {
        val intent = Intent(this, log_in::class.java)
        startActivity(intent)
        finish()
    }
}
