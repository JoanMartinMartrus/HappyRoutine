package com.example.happyroutine
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_log_in.*

private lateinit var auth: FirebaseAuth

class log_in : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        auth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser : FirebaseUser?){
        if(currentUser != null){
            startActivity(Intent(this, routine::class.java))
        }
        else{
            Toast.makeText(baseContext, "Login failed.", Toast.LENGTH_SHORT).show()
        }
    }

    fun goToRoutine(view: View) {
        if(text_email.text.toString().isEmpty()){
            text_email.error = "Please, enter your e-mail"
            text_email.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(text_email.text.toString()).matches()){
            text_email.error = "Please, enter a valid e-mail"
            text_email.requestFocus()
            return
        }

        if(text_password.text.toString().isEmpty()){
            text_password.error = "Please, enter your password"
            text_password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(text_email.text.toString(), text_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Login failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // ...
            }
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
