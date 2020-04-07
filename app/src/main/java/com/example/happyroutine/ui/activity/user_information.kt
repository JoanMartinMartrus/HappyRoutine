package com.example.happyroutine.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.happyroutine.R
import com.example.happyroutine.ui.dialog.DatePickerFragment
import kotlinx.android.synthetic.main.activity_user_information.*

class user_information : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        edit_text_birthday.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            edit_text_birthday.setText(selectedDate)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun goToUserExercise(view: View) {
        val intent = Intent(this, routine::class.java)
        startActivity(intent)
    }

}
