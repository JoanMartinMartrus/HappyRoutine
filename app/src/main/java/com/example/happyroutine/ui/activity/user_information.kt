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

        checkBoxPlatformAnswer.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                linearlayout_platform.visibility=View.VISIBLE
            }else {
                linearlayout_platform.visibility=View.INVISIBLE
            }
        }
    }

    fun goToUserExercise(view: View) {
        clearErrors()
        if (checkFields()) {
            saveData()
        }

    }

    private fun saveData() {
        
    }


    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            edit_text_birthday.setText(selectedDate)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }



    private fun checkFields(): Boolean{

        if (edit_text_name.text.toString().isEmpty()) {
            edit_text_name.error = "Please enter the name"
            edit_text_name.requestFocus()
            return false
        }

        if (edit_text_surname.text.toString().isEmpty()) {
            edit_text_surname.error = "Please enter the surname"
            edit_text_surname.requestFocus()
            return false
        }

        if (radiogroup_gender.checkedRadioButtonId == -1) {
            tv_gender.setError("Please choose gender")
            tv_gender.requestFocus()
            return false
        }

        if (edit_text_birthday.text.toString().isEmpty()) {
            edit_text_birthday.error = "Please enter your birthday"
            edit_text_birthday.requestFocus()
            return false
        }

        if (edit_text_location.text.toString().isEmpty()) {
            edit_text_location.error = "Please enter your city"
            edit_text_location.requestFocus()
            return false
        }

        if (edit_text_height.text.toString().isEmpty()) {
            edit_text_height.error = "Please enter your height"
            edit_text_height.requestFocus()
            return false
        }

        if (edit_text_weight.text.toString().isEmpty()) {
            edit_text_weight.error = "Please enter your weight"
            edit_text_weight.requestFocus()
            return false
        }

        if ((rg_line1.checkedRadioButtonId==-1) and (rg_line2.checkedRadioButtonId==-1)) {
            tv_objectives.setError("Please choose objective")
            tv_objectives.requestFocus()
            return false
        }

        if ( (!offerExerciseAdviceCheckbox.isChecked and !offerDietAdviceCheckbox.isChecked and
                    !offerCompanyCheckbox.isChecked) and checkBoxPlatformAnswer.isChecked ) {
            tv_offer.setError("Select at least one item from offer")
            tv_offer.requestFocus()
            return false
        }

        if ( (!needsExerciseAdviceCheckbox.isChecked and !needsDietAdviceCheckbox.isChecked and
                    !needsCompanyCheckbox.isChecked) and checkBoxPlatformAnswer.isChecked ) {
            tv_needs.setError("Select at least one item from needs")
            tv_needs.requestFocus()
            return false
        }

        return true
    }

    private fun clearErrors() {
        edit_text_name.setError(null)
        edit_text_surname.setError(null)
        tv_gender.setError(null)
        edit_text_birthday.setError(null)
        edit_text_location.setError(null)
        edit_text_weight.setError(null)
        edit_text_height.setError(null)
        tv_objectives.setError(null)
        tv_offer.setError(null)
        tv_needs.setError(null)
    }

}
