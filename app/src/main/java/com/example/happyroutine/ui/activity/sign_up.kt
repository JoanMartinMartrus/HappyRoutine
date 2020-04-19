package com.example.happyroutine.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.happyroutine.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class sign_up : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
                    // If SignUp is succesful, automatically, the user log in

                    auth.signInWithEmailAndPassword(tv_text_email.text.toString(), tv_text_password.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                updateUI(user)
                            } else {
                                Toast.makeText(baseContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                        }
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

    private fun updateUI(currentUser : FirebaseUser?){
        if(currentUser != null){
            startActivity(Intent(this, user_information::class.java))
            finish()
        }
    }
}
