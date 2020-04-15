package com.example.happyroutine.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.happyroutine.R
import com.example.happyroutine.ui.dialog.DatePickerFragment
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_information.*
import java.util.*

class user_information : AppCompatActivity() {

    lateinit var db : CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        db = FirebaseFirestore.getInstance().collection("users")

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
            storeUserInformationData()
        }
    }

    private fun storeUserInformationData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val name = edit_text_name.text.toString()
        val surname = edit_text_surname.text.toString()
        val email = FirebaseAuth.getInstance().currentUser?.email
        val gender = findViewById<RadioButton>(radiogroup_gender.checkedRadioButtonId).text.toString()
        val day = edit_text_birthday.text.toString().split("/")[0].trim().toInt()
        val month = edit_text_birthday.text.toString().split("/")[1].trim().toInt() - 1 // January is 0
        val year = edit_text_birthday.text.toString().split("/")[2].trim().toInt()
        val date = Date(year, month, day)
        val location = edit_text_location.text.toString()
        val height = edit_text_height.text.toString().toInt()
        val objective = getObjective()
        val diet = getDiet()
        val participantPlatform = checkBoxPlatformAnswer.isChecked
        val offer = getOffer()
        val needs = getNeeds()
        var weightEntries = ArrayList<Float>()
        weightEntries.add(edit_text_weight.text.toString().toFloat())
        var dateEntries = ArrayList<Timestamp>()
        dateEntries.add(Timestamp.now())


        val data = hashMapOf(
            "uid" to uid,
            "name" to name,
            "surname" to surname,
            "email" to email,
            "gender" to gender,
            "birthday" to date,
            "location" to location,
            "height" to height,
            "objective" to objective,
            "diet" to diet,
            "participantPlatform" to participantPlatform,
            "offer" to offer,
            "needs" to needs
        )

        val weightTrack = hashMapOf(
            "weightEntries" to weightEntries,
            "dateEntries" to dateEntries
        )

       db.document(uid)
           .set(data)
           .addOnSuccessListener {
               Log.i("DB", "Data stored succesfully")
               startActivity(Intent(this, Navigation_bar_main::class.java))
               finish()
           }
           .addOnFailureListener {e ->
               Log.i("DB", "Something went wrong",e)
               Toast.makeText(baseContext, "Something went wrong" + e.toString(),
                   Toast.LENGTH_LONG).show()
           }

        db.document(uid).collection("weight").document("weight")
            .set(weightTrack)
            .addOnSuccessListener {
                Log.i("DB", "Data stored succesfully")
                startActivity(Intent(this, Navigation_bar_main::class.java))
                finish()
            }
            .addOnFailureListener {e ->
                Log.i("DB", "Something went wrong",e)
                Toast.makeText(baseContext, "Something went wrong" + e.toString(),
                    Toast.LENGTH_LONG).show()
            }
    }

    private fun getOffer(): List<String> {
        var offerList = mutableListOf<String>()
        if (offerCompanyCheckbox.isChecked) {
            offerList.add(offerCompanyCheckbox.text.toString())
        }
        if (offerDietAdviceCheckbox.isChecked) {
            offerList.add(offerDietAdviceCheckbox.text.toString())
        }
        if (offerExerciseAdviceCheckbox.isChecked) {
            offerList.add(offerExerciseAdviceCheckbox.text.toString())
        }
        return offerList
    }


    private fun getNeeds(): List<String> {
        var needsList = mutableListOf<String>()
        if (needsCompanyCheckbox.isChecked) {
            needsList.add(needsCompanyCheckbox.text.toString())
        }
        if (needsDietAdviceCheckbox.isChecked) {
            needsList.add(needsDietAdviceCheckbox.text.toString())
        }
        if (needsExerciseAdviceCheckbox.isChecked) {
            needsList.add(needsExerciseAdviceCheckbox.text.toString())
        }
        return needsList
    }

    private fun getDiet(): List<String> {
        var dietList  = mutableListOf<String>()
        if (checkBoxLactoseIntolerant.isChecked) {
            dietList.add(checkBoxLactoseIntolerant.text.toString())
        }
        if (checkBoxCeliac.isChecked) {
            dietList.add(checkBoxCeliac.text.toString())
        }
        if (checkBoxVegetarian.isChecked) {
            dietList.add(checkBoxVegetarian.text.toString())
        }
        return dietList
    }

    private fun getObjective(): String {
        if (rg_line1.checkedRadioButtonId != -1 ) {
            return findViewById<RadioButton>(rg_line1.checkedRadioButtonId).text.toString()
        }

        return findViewById<RadioButton>(rg_line2.checkedRadioButtonId).text.toString()
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
