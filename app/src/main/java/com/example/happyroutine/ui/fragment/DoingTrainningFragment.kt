package com.example.happyroutine.ui.fragment

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction

import com.example.happyroutine.R
import com.example.happyroutine.model.Trainning
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.Date
import kotlinx.android.synthetic.main.fragment_doing_trainning.*
import java.time.LocalDate
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class DoingTrainningFragment  ( val name: String) : Fragment() {

    lateinit var db : CollectionReference
    var trainning: Trainning =
        Trainning(
            "",
            "",
            false,
            false,
            "",
            ArrayList(),
            "",
            ""
        )
    var currentExercice:Int=0
    lateinit var user : DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View= inflater.inflate(R.layout.fragment_doing_trainning, container, false)
        db= FirebaseFirestore.getInstance().collection("Trainning")
        user= FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().currentUser?.uid.toString())
        getTrainning(name,view)
        val button: Button =view.findViewById(R.id.done)
        button.visibility=View.INVISIBLE
        var fragment: FrameLayout =view.findViewById(R.id.exercise)
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        var height = displayMetrics.heightPixels
        fragment.layoutParams.height=height-(1000)
        // Inflate the layout for this fragment
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            trainning?.favourite =checkBox.isChecked
            trainning?.let { update() }
        }
        previous.setOnClickListener {
            if((currentExercice-1)>=0){
                currentExercice--
                changeExercici()
            }else{
                Toast.makeText(view.context,"there is not previous", Toast.LENGTH_SHORT).show()
            }
        }
        next.setOnClickListener {
            if(currentExercice+1< trainning.exercises.size){
                currentExercice++
                progressBar2.progress=currentExercice
                changeExercici()
            }else{
                progressBar2.progress=currentExercice+1
                val button: Button =view.findViewById(R.id.done)
                button.visibility=View.VISIBLE
                Toast.makeText(view.context,"there is not next", Toast.LENGTH_SHORT).show()
            }
        }
        done.setOnClickListener {
            trainning.isDone=true
            update()
            setAndSaveTrainning()
            fragmentManager?.let {
                it.beginTransaction().replace(R.id.frame_layout_navigation_bar,TrainningFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }
    }
    fun getTrainning(name:String, view:View){
        db.whereEqualTo("name", name).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    trainning= Trainning(
                        document.get("name").toString(),
                        document.get("advice").toString(),
                        document.get("favourite").toString().toBoolean(),
                        document.get("isDone").toString().toBoolean(),
                        document.get("level").toString(),
                        document["exercises"] as ArrayList<String>,
                        document.get("objectives").toString(),
                        document.id
                    )
                    paintScreen(trainning,view)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    private fun paintScreen(trainning: Trainning, view: View) {
        val name: TextView =view.findViewById(R.id.name)
        name.text=trainning.name
        val favourite: CheckBox =view.findViewById(R.id.checkBox)
        favourite.isChecked= trainning.favourite!!
        progressBar2.max=trainning.exercises.size
        progressBar2.progress=currentExercice
        changeExercici()
    }

    fun  changeExercici(){
        childFragmentManager.beginTransaction().replace(R.id.exercise,ShowExerciceFragment(trainning.exercises.get(currentExercice)))
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun update(){
        db.document(trainning.id).update("favourite", trainning.favourite)
    }

    private fun setAndSaveTrainning(){
        val data = hashMapOf(
            "name" to trainning.name,
            "year" to LocalDate.now().year,
            "month" to LocalDate.now().monthValue,
            "day" to LocalDate.now().dayOfMonth
        )
        user.collection("trainningData").add(data).addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

}
