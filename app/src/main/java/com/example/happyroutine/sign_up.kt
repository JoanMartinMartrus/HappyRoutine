package com.example.happyroutine

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_sign_up.*

class sign_up : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        button_signUp.setOnClickListener {
            signUpUser()
        }

    }

    private fun signUpUser() {

        if(tv_text_email.text.toString().isEmpty()) {
            tv_text_email.error = "Please enter the email"
            tv_text_email.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tv_text_email.text.toString()).matches()) {
            tv_text_email.error = "Please enter email"
            tv_text_email.requestFocus()
            return

        }

        if (text_confirmPassword.text.toString().isEmpty()) {
            text_confirmPassword.error = "Please enter password again"
            text_confirmPassword.requestFocus()
            return
        }


        if (tv_text_password.text.toString() != text_confirmPassword.text.toString()) {
            text_confirmPassword.error = "The two passwords do not match"
            text_confirmPassword.requestFocus()
            return
        }


        auth.createUserWithEmailAndPassword(tv_text_email.text.toString(), tv_text_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, log_in::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Sign Up failed. Try again after some time /n" + task.exception.toString(),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun goToLogIn(view: View) {
        val intent = Intent(this, log_in::class.java)
        startActivity(intent)
    }


    fun signUpAndGoUserInformation(view: View) {


        val intent = Intent(this, user_information::class.java)
        startActivity(intent)
    }
}
