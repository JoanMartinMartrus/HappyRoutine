package com.example.happyroutine.ui.fragment

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.happyroutine.R
import com.example.happyroutine.model.Exercice
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_show_exercice.*

/**
 * A simple [Fragment] subclass.
 */
class ShowExerciceFragment( val name: String) : Fragment() {

    lateinit var db : CollectionReference
    var exercice: Exercice?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View=inflater.inflate(R.layout.fragment_show_exercice, container, false)

        db= FirebaseFirestore.getInstance().collection("exerciseOrStretching")
        getExercice(name,view)

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            exercice?.favourite =checkBox.isChecked
            exercice?.let { setAndSaveData(it) }
        }
    }

    private fun paintScreen(exercice: Exercice, view: View){
        val name: TextView =view.findViewById(R.id.name)
        name.text=exercice.name
        val objective: TextView =view.findViewById(R.id.objective)
        if(exercice.objectives.equals("GET_STRONG")){
            objective.text= "get stronger"
        }
        //TODO: dependiendo del objetivo escribo una cosa o otra preguntar a joan los objetivos finales
        val level: TextView =view.findViewById(R.id.level)
        level.text= exercice.level?.toLowerCase() ?: ""
        val description: TextView =view.findViewById(R.id.description)
        description.text=exercice.description
        val favourite:CheckBox=view.findViewById(R.id.checkBox)
        favourite.isChecked= exercice.favourite!!

        //video
        val video:VideoView=view.findViewById(R.id.video)
        try {
            val mediaController:MediaController= MediaController(view.context)
            video.setMediaController(mediaController)
            mediaController.setAnchorView(video)
            video.setVideoURI(Uri.parse(exercice.gifURL))
            video.start()
        }catch (e:Exception){
            Toast.makeText(view.context,"Error: "+e.message,Toast.LENGTH_LONG).show()
        }


    }


    private  fun getExercice(name:String,view: View) {
        db.whereEqualTo("name", name).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    exercice= Exercice(
                        document.get("name").toString(),
                        document.get("description").toString(),
                        document.get("favourite").toString().toBoolean(),
                        document.get("gifURL").toString(),
                        document.get("level").toString(),
                        document.get("muscles").toString(),
                        document.get("objectives").toString(),
                        document.id
                    )
                    paintScreen(exercice!!,view )
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    private fun setAndSaveData(exercice: Exercice){
        val data = hashMapOf(
            "name" to exercice.name,
            "description" to exercice.description,
            "favourite" to exercice.favourite,
            "gifURL" to exercice.gifURL,
            "level" to exercice.level,
            "muscles" to exercice.muscles,
            "objectives" to exercice.objectives
        )
        db.document(exercice.id).set(data)
    }

}
