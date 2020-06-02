package com.example.happyroutine.ui.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.example.happyroutine.R
import com.example.happyroutine.ui.dialog.DatePickerFragment
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_user_information.*
import kotlinx.android.synthetic.main.activity_edit_user_information.rg_line1
import kotlinx.android.synthetic.main.activity_edit_user_information.tv_needs
import java.text.SimpleDateFormat
import java.util.*

class EditUserInformation : AppCompatActivity() {
    lateinit var db : CollectionReference

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_edit_user_information)

        db = FirebaseFirestore.getInstance().collection("users")

        edit_text_birthday2.setOnClickListener {
            showDatePickerDialog()
        }

        checkBoxPlatformAnswer2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                linearlayout_platform2.visibility= View.VISIBLE
            }else {
                linearlayout_platform2.visibility= View.INVISIBLE
            }
        }
        if(FirebaseAuth.getInstance().currentUser != null){
            loadData()
        }
    }

    fun goToProfile(view: View) {
        clearErrors()
        if(checkFields() && FirebaseAuth.getInstance().currentUser != null){
            updateUserInformationData()
        }
    }

    private fun loadData(){
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        db.document(uid).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                val document = task.result
                if (document!!.exists()) {
                    edit_text_name2.text = Editable.Factory.getInstance().newEditable(document["name"].toString())
                    edit_text_surname2.text = Editable.Factory.getInstance().newEditable(document["surname"].toString())
                    val birthday = document["birthday"] as Timestamp
                    val format = SimpleDateFormat("dd / MM / yyyy")
                    edit_text_birthday2.text = Editable.Factory.getInstance().newEditable(format.format(birthday.toDate()).toString())
                    edit_text_height2.text = Editable.Factory.getInstance().newEditable(String.format("%.1f", document["height"]))
                    db.document(uid).collection("weight").document("weight").get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val document = task.result
                                if (document!!.exists()) {
                                    val weightEntries = document["weightEntries"] as ArrayList<Float>
                                    edit_text_weight2.text = Editable.Factory.getInstance()
                                        .newEditable(String.format("%.1f", weightEntries[weightEntries.lastIndex]))
                                }
                            }
                        }
                    edit_text_location2.text = Editable.Factory.getInstance().newEditable(document["location"].toString())

                    val gender = document["gender"].toString()
                    if(gender == "Male"){
                        radioButtonMale2.isChecked = true
                    }
                    else{
                        radioButtonFemale2.isChecked = true
                    }

                    val objective = document["objective"].toString()
                    if(objective == "Gain muscle"){
                        rb_gain_muscle2.isChecked = true
                    }
                    if(objective == "Gain weight"){
                        rb_gain_weight2.isChecked = true
                    }
                    if(objective == "Lose weight"){
                        rb_lose_weight2.isChecked = true
                    }
                    if(objective == "Exercise for health"){
                        rb_exercise_for_muscle2.isChecked = true
                    }

                    val diets = document["diet"] as ArrayList<String>
                    if(diets.isNotEmpty()){
                        for(diet in diets) {
                            if (diet == "Celiac") {
                                checkBoxCeliac2.isChecked = true
                            }
                            if (diet == "Lactose intolerant") {
                                checkBoxLactoseIntolerant2.isChecked = true
                            }
                            if (diet == "Vegetarian") {
                                checkBoxVegetarian2.isChecked = true
                            }
                        }
                    }

                    val participant = document["participantPlatform"] as Boolean
                    if(participant){
                        checkBoxPlatformAnswer2.isChecked = true
                        linearlayout_platform2.visibility= View.VISIBLE

                        val needs = document["needs"] as ArrayList<String>
                        for(need in needs){
                            if(need == "Diet Advice"){
                                needsDietAdviceCheckbox2.isChecked = true
                            }
                            if(need == "Exercise advice"){
                                needsExerciseAdviceCheckbox2.isChecked = true
                            }
                            if(need == "Company"){
                                needsCompanyCheckbox2.isChecked = true
                            }
                        }

                        val offers = document["offer"] as ArrayList<String>
                        for(offer in offers){
                            if(offer == "Diet Advice"){
                                offerDietAdviceCheckbox2.isChecked = true
                            }
                            if(offer == "Exercise advice"){
                                offerExerciseAdviceCheckbox2.isChecked = true
                            }
                            if(offer == "Company"){
                                offerCompanyCheckbox2.isChecked = true
                            }
                        }
                    }
                    else{
                        checkBoxPlatformAnswer2.isChecked = false
                        linearlayout_platform2.visibility= View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun updateUserInformationData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val name = edit_text_name2.text.toString()
        val surname = edit_text_surname2.text.toString()
        val email = FirebaseAuth.getInstance().currentUser?.email
        val gender = findViewById<RadioButton>(radiogroup_gender2.checkedRadioButtonId).text.toString()
        val day = edit_text_birthday2.text.toString().split("/")[0].trim().toInt()
        val month = edit_text_birthday2.text.toString().split("/")[1].trim().toInt() - 1 // January is 0
        val year = edit_text_birthday2.text.toString().split("/")[2].trim().toInt()
        val date = Date((year-1900), month, day)
        val location = edit_text_location2.text.toString()
        val height = edit_text_height2.text.toString().toFloat()
        val objective = getObjective()
        val diet = getDiet()
        val participantPlatform = checkBoxPlatformAnswer2.isChecked
        val offer = getOffer()
        val needs = getNeeds()

        var weightEntries = ArrayList<Float>()
        var dateEntries = ArrayList<Timestamp>()

        db.document(uid).collection("weight").document("weight").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        weightEntries = document["weightEntries"] as ArrayList<Float>
                        dateEntries = document["dateEntries"] as ArrayList<Timestamp>
                    }
                    dateEntries.add(Timestamp.now())
                    weightEntries.add(edit_text_weight2.text.toString().toFloat())

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
                    db.document(uid).update(data)
                    db.document(uid).collection("weight").document("weight")
                        .update("weightEntries", weightEntries,"dateEntries", dateEntries)

                    startActivity(Intent(this, Navigation_bar_main::class.java))
                    finish()
                }
            }
    }

    private fun getOffer(): List<String> {
        var offerList = mutableListOf<String>()
        if (offerCompanyCheckbox2.isChecked) {
            offerList.add(offerCompanyCheckbox2.text.toString())
        }
        if (offerDietAdviceCheckbox2.isChecked) {
            offerList.add(offerDietAdviceCheckbox2.text.toString())
        }
        if (offerExerciseAdviceCheckbox2.isChecked) {
            offerList.add(offerExerciseAdviceCheckbox2.text.toString())
        }
        return offerList
    }


    private fun getNeeds(): List<String> {
        var needsList = mutableListOf<String>()
        if (needsCompanyCheckbox2.isChecked) {
            needsList.add(needsCompanyCheckbox2.text.toString())
        }
        if (needsDietAdviceCheckbox2.isChecked) {
            needsList.add(needsDietAdviceCheckbox2.text.toString())
        }
        if (needsExerciseAdviceCheckbox2.isChecked) {
            needsList.add(needsExerciseAdviceCheckbox2.text.toString())
        }
        return needsList
    }

    private fun getDiet(): List<String> {
        var dietList  = mutableListOf<String>()
        if (checkBoxLactoseIntolerant2.isChecked) {
            dietList.add(checkBoxLactoseIntolerant2.text.toString())
        }
        if (checkBoxCeliac2.isChecked) {
            dietList.add(checkBoxCeliac2.text.toString())
        }
        if (checkBoxVegetarian2.isChecked) {
            dietList.add(checkBoxVegetarian2.text.toString())
        }
        return dietList
    }

    private fun getObjective(): String {
        if (rg_line1.checkedRadioButtonId != -1 ) {
            return findViewById<RadioButton>(rg_line1.checkedRadioButtonId).text.toString()
        }

        return findViewById<RadioButton>(rg_line22.checkedRadioButtonId).text.toString()
    }


    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            print("ANY: ")
            println(year)
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            edit_text_birthday2.setText(selectedDate)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }



    private fun checkFields(): Boolean{

        if (edit_text_name2.text.toString().isEmpty()) {
            edit_text_name2.error = "Please enter the name"
            edit_text_name2.requestFocus()
            return false
        }

        if (edit_text_surname2.text.toString().isEmpty()) {
            edit_text_surname2.error = "Please enter the surname"
            edit_text_surname2.requestFocus()
            return false
        }

        if (radiogroup_gender2.checkedRadioButtonId == -1) {
            tv_gender2.setError("Please choose gender")
            tv_gender2.requestFocus()
            return false
        }

        if (edit_text_birthday2.text.toString().isEmpty()) {
            edit_text_birthday2.error = "Please enter your birthday"
            edit_text_birthday2.requestFocus()
            return false
        }

        if(edit_text_birthday2.text.toString().isNotEmpty() && checkAge() < 18){
            edit_text_birthday2.error = "You must be over 18"
            Toast.makeText(baseContext, "You must be over 18", Toast.LENGTH_SHORT).show()
            edit_text_birthday2.requestFocus()
            return false
        }

        if (edit_text_location2.text.toString().isEmpty()) {
            edit_text_location2.error = "Please enter your city"
            edit_text_location2.requestFocus()
            return false
        }

        if (edit_text_height2.text.toString().isEmpty()) {
            edit_text_height2.error = "Please enter your height"
            edit_text_height2.requestFocus()
            return false
        }

        if (edit_text_height2.text.toString().isNotEmpty()) {
            val number = edit_text_height2.text.toString().toFloatOrNull()
            if(number == null){
                edit_text_height2.error = "Please enter a number"
                edit_text_height2.requestFocus()
                return false
            }
        }

        if (edit_text_weight2.text.toString().isEmpty()) {
            edit_text_weight2.error = "Please enter your weight"
            edit_text_weight2.requestFocus()
            return false
        }

        if (edit_text_weight2.text.toString().isNotEmpty()) {
            val number = edit_text_weight2.text.toString().toFloatOrNull()
            if(number == null){
                edit_text_weight2.error = "Please enter a number"
                edit_text_weight2.requestFocus()
                return false
            }
        }

        if ((rg_line1.checkedRadioButtonId==-1) and (rg_line22.checkedRadioButtonId==-1)) {
            tv_objectives2.setError("Please choose objective")
            tv_objectives2.requestFocus()
            return false
        }

        if ( (!offerExerciseAdviceCheckbox2.isChecked and !offerDietAdviceCheckbox2.isChecked and
                    !offerCompanyCheckbox2.isChecked) and checkBoxPlatformAnswer2.isChecked ) {
            tv_offer2.setError("Select at least one item from offer")
            tv_offer2.requestFocus()
            return false
        }

        if ( (!needsExerciseAdviceCheckbox2.isChecked and !needsDietAdviceCheckbox2.isChecked and
                    !needsCompanyCheckbox2.isChecked) and checkBoxPlatformAnswer2.isChecked ) {
            tv_needs.setError("Select at least one item from needs")
            tv_needs.requestFocus()
            return false
        }

        return true
    }

    private fun clearErrors() {
        edit_text_name2.setError(null)
        edit_text_surname2.setError(null)
        tv_gender2.setError(null)
        edit_text_birthday2.setError(null)
        edit_text_location2.setError(null)
        edit_text_weight2.setError(null)
        edit_text_height2.setError(null)
        tv_objectives2.setError(null)
        tv_offer2.setError(null)
        tv_needs.setError(null)
    }

    private fun checkAge(): Int{
        val day = edit_text_birthday2.text.toString().split("/")[0].trim().toInt()
        val month = edit_text_birthday2.text.toString().split("/")[1].trim().toInt() - 1
        val year = edit_text_birthday2.text.toString().split("/")[2].trim().toInt()
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        val checkMonth = if(currentMonth < month){
            1
        } else{
            0
        }
        val checkDay = if(currentDay < day){
            1
        } else{
            0
        }
        return (currentYear - year - checkMonth - checkDay)
    }
}