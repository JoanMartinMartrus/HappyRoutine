package com.example.happyroutine.ui.activity
import android.annotation.SuppressLint
import android.content.Context
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
import kotlinx.android.synthetic.main.activity_log_in.*


class log_in : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_log_in)
        auth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        checkBox_keepMeLoggedIn.isChecked = loadCheckBox(checkBox_keepMeLoggedIn.text.toString())
        if(checkBox_keepMeLoggedIn.isChecked) {
            val currentUser = auth.currentUser
            updateUI(currentUser)
        }
    }

    private fun updateUI(currentUser : FirebaseUser?){
        if(currentUser != null){
            startActivity(Intent(this, Navigation_bar_main::class.java))
        }
    }

    fun goToPrincipalView(view: View) {
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

        saveCheckbox(checkBox_keepMeLoggedIn.isChecked, checkBox_keepMeLoggedIn.text.toString())
        auth.signInWithEmailAndPassword(text_email.text.toString(), text_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Login failed. User not found", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun goToSignUp(view: View) {
        val intent = Intent(this, sign_up::class.java)
        startActivity(intent)
        finish()
    }

    fun goToForgotPassword(view: View) {
        val intent = Intent(this, forgot_my_password::class.java)
        startActivity(intent)
    }

    fun saveCheckbox(isChecked: Boolean, key: String){
        val sharedpreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putBoolean(key, isChecked)
        editor.apply()

    }

    fun loadCheckBox(key: String):Boolean{
        val sharedpreferences = getPreferences(Context.MODE_PRIVATE)
        return sharedpreferences.getBoolean(key, false)
    }


}
