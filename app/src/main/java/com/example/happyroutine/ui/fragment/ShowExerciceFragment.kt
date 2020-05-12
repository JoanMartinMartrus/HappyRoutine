package com.example.happyroutine.ui.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.happyroutine.R
import com.example.happyroutine.ui.model.Exercice
import com.example.happyroutine.ui.model.ShowExerciciComunication
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class ShowExerciceFragment: Fragment(),ShowExerciciComunication {

    lateinit var db : CollectionReference
    var exercice:Exercice?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View=inflater.inflate(R.layout.fragment_show_exercice, container, false)

        db= FirebaseFirestore.getInstance().collection("exerciseOrStretching")
        //exercice=getExercice(name)
        // Inflate the layout for this fragment
        return view
    }

    private  fun getExercice(name:String): Exercice? {
        var exercice:Exercice?=null
        db.whereEqualTo("name", name).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    exercice= Exercice( document.get("name").toString(), document.get("description").toString(),
                        document.get("favourite").toString().toBoolean(), document.get("gifURL").toString(),
                        document.get("level").toString(), document.get("token").toString(),
                        document.get("muscles").toString(),document.get("objectives").toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
        return exercice
    }

    private fun setAndSaveData(exercice:Exercice){
        val data = hashMapOf(
            "name" to exercice.name,
            "description" to exercice.description,
            "favourite" to exercice.favourite,
            "gifURL" to exercice.gifURL,
            "level" to exercice.level,
            "token" to exercice.token,
            "muscles" to exercice.muscles,
            "objectives" to exercice.objectives
        )
        db.document(exercice.name ).set(data)
    }

    override fun openExercice(name: String) {
        TODO("Not yet implemented")
    }
}
