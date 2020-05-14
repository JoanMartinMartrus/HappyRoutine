package com.example.happyroutine.model

import android.util.Log
import com.example.happyroutine.model.enums.DietType
import com.example.happyroutine.model.enums.GenderType
import com.example.happyroutine.model.enums.ObjectiveType
import com.example.happyroutine.model.enums.OfferType
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AppData() {

    private val foodDb = FirebaseFirestore.getInstance().collection("food")
    private val userDb = FirebaseFirestore.getInstance().collection("users")


    var user: User = User()
    lateinit var foodList : List<Food>


    fun initData() {
        var uid = FirebaseAuth.getInstance().currentUser?.uid
        // TODO check if uid is not null

        // init user
        var query = userDb.whereEqualTo("uid", uid)
        query.get().addOnCompleteListener() {task ->
            if (task.isSuccessful) {
                for (doc in task.result!!.documents) { //TODO correct !!
                    user.uid = doc.get("uid").toString()
                    user.name = doc.get("name").toString()
                    user.surname = doc.get("surname").toString()
                    user.email = doc.get("email").toString()
                    user.dateOfBirth = doc.get("birthday") as Timestamp?
                    user.gender = genderTypeByString(doc.get("gender").toString())
                     if (doc.get("icon_url") != null) {
                         user.iconUserURL=doc.get("icon_url").toString()

                     } else {
                         user.iconUserURL = ""
                     }
                    user.platform = doc.get("participantPlatform") as Boolean?

                    user.diet = mutableListOf<DietType>()
                    for (diet in (doc.get("diet") as List<String>)) {
                        var dietType = dietTypeByString(diet)
                        (user.diet as MutableList).add(dietType)

                    }

                    user.offers = mutableListOf<OfferType>()
                    for (offer in (doc.get("offer") as List<String>)) {
                        var offerType = offerTypeByString(offer)
                        (user.offers as MutableList).add(offerType)

                    }

                    user.needs = mutableListOf<OfferType>()
                    for (need in (doc.get("needs") as List<String>)) {
                        var offerType = offerTypeByString(need)
                        (user.needs as MutableList).add(offerType)

                    }

                    user.objective = objectiveTypeByString(doc.get("objective").toString())

                    //init food
                    query = foodDb.whereArrayContains("objectives", "Gain weight") // TODO !! correct user.objective?.objectiveName!!
                    query.get().addOnCompleteListener {task ->
                        if (task.isSuccessful) {
                            foodList = task.result!!.toObjects(Food::class.java)
                            for (food in foodList) {
                                Log.i("DB----->", food.name)
                                Log.i("DB----->", food.objectives.toString())
                                Log.i("DB----->", food.diet.toString())
                            }

                            query = foodDb.whereArrayContains("objectives", "Lose weight") // TODO !! correct
                            query.get().addOnCompleteListener {task ->
                                if (task.isSuccessful) {
                                    foodList = (foodList intersect task.result!!.toObjects(Food::class.java)).toList()
                                    Log.i("DB2----->", foodList.toString())
                                    for (food in foodList) {
                                        Log.i("DB----->", food.name)
                                        Log.i("DB----->", food.objectives.toString())
                                        Log.i("DB----->", food.diet.toString())
                                    }
                                }

                            }

                    }


                    }

                }
            }

        }


    }

    private fun objectiveTypeByString(objective: String): ObjectiveType {
        when (objective) {
            "Exercise for health" -> return ObjectiveType.EXERCISE_FOR_HEALTH
            "Gain weight" -> return ObjectiveType.GAIN_WEIGHT
            "Lose weight" -> return ObjectiveType.LOSE_WEIGHT
            "Gain muscle" -> return ObjectiveType.GAIN_MUSCLE
            else -> return ObjectiveType.UNKNOWN
        }
    }

    private fun offerTypeByString(offer: String): OfferType {
        when (offer) {
            "Diet Advice" -> return OfferType.DIET_ADVICE
            "Exercise advice" -> return OfferType.EXERCISE_ADVICE
            "Company" -> return OfferType.COMPANY
            else -> return OfferType.NONE
        }
    }

    private fun genderTypeByString(gender: String): GenderType {
        when (gender) {
            "Male" -> return GenderType.MALE
            "Female" -> return GenderType.FEMALE
            else -> return GenderType.UNKNOWN
        }
    }

    private  fun dietTypeByString(diet: String): DietType {
        when (diet) {
            "Vegetarian" -> return DietType.VEGETARIAN
            "Celiac" -> return DietType.CELIAC
            "Lactose Intolerant" -> return DietType.LACTOSE_INTOLERANT
            else -> return DietType.UNKNOWN
        }
    }



}