package com.example.happyroutine.ui.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.happyroutine.R
import com.example.happyroutine.model.Trainning
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import io.grpc.Context
import kotlinx.android.synthetic.main.fragment_show_exercice.checkBox
import kotlinx.android.synthetic.main.fragment_show_trainning.*

/**
 * A simple [Fragment] subclass.
 */
class ShowTrainningFragment ( val name: String) : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View= inflater.inflate(R.layout.fragment_show_trainning, container, false)
        db= FirebaseFirestore.getInstance().collection("Trainning")
        getTrainning(name,view)
        var fragment:FrameLayout=view.findViewById(R.id.exercise)
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        var height = displayMetrics.heightPixels
        fragment.layoutParams.height=height-(1280)
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
                changeExercici()
            }else{
                Toast.makeText(view.context,"there is not next", Toast.LENGTH_SHORT).show()
            }
        }
        hacer.setOnClickListener {
            fragmentManager?.let {
                it.beginTransaction().replace(R.id.frame_layout_navigation_bar,DoingTrainningFragment(name))
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
        val objective: TextView =view.findViewById(R.id.objective)
        if(trainning.objectives.equals("GET_STRONG")){
            objective.text= "get stronger"
        }
        val advice: TextView =view.findViewById(R.id.advice)
        advice.text=trainning.advice
        val favourite: CheckBox =view.findViewById(R.id.checkBox)
        favourite.isChecked= trainning.favourite!!
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

}
