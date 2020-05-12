package com.example.happyroutine.ui.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.FragmentTransaction

import com.example.happyroutine.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_estadisticas_trainning_days.*
import kotlinx.android.synthetic.main.fragment_exercices.*
import kotlinx.android.synthetic.main.fragment_trainning.*
import kotlin.math.absoluteValue

/**
 * A simple [Fragment] subclass.
 */
class ExercicesFragment : Fragment() {

    lateinit var db : CollectionReference
    var fullBody:ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_exercices, container, false)

        db= FirebaseFirestore.getInstance().collection("exerciseOrStretching")

        getQueryWhere(R.id.recommended_spinner,"Recommended",view,"Recommended",db)
        getQueryWhere(R.id.my_favs_spinner,"My Favourites",view,"My Favourites",db)
        getQueryWhere(R.id.Back,"Back",view,"CARDIO",db)
        getQueryWhere(R.id.Abds,"Abds",view,"ABDS",db)
        getQueryWhere(R.id.Arms,"Arms",view,"ARMS",db)
        getQueryWhere(R.id.Legs,"Legs",view,"LEGS",db)
        getQueryWhere(R.id.Chest,"Chest",view,"CHEST",db)
        getQueryWhere(R.id.Stretching,"Stretching",view,"STRETCHING",db)
        getQueryWhere(R.id.Buttocks,"Buttocks",view,"BUTTOCKS",db)
        getQueryWhere(R.id.Cardio,"Cardio",view,"CARDIO",db)
        getQueryWhere(R.id.FullBody,"Full Body",view,"FULLBODY",db)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*FullBody.setOnItemClickListener { parent, view1, position, id ->
            //println("\n imprimo name: "+position);
        }*/
    }

    private fun getQueryWhere(id_spinner:Int, title_spinner:String, view:View, name:String, db: CollectionReference) {
        if(title_spinner.equals("Recommended")){
            val spinner: Spinner = view.findViewById(id_spinner)
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                view.context,
                R.layout.custom_spinner
            )
            adapter.add(title_spinner)
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
            spinner.adapter = adapter
        }else if(title_spinner.equals("My Favourites")){
            val spinner: Spinner = view.findViewById(id_spinner)
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                view.context,
                R.layout.custom_spinner
            )
            adapter.add(title_spinner)
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
            spinner.adapter = adapter
        }else {
            var i: Int = 1
            val spinner: Spinner = view.findViewById(id_spinner)
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                view.context,
                R.layout.custom_spinner
            )
            adapter.add(title_spinner)
            db.whereEqualTo("muscles", name).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        fullBody.add(document.get("name").toString())
                        adapter.add(i.toString() + ". " + document.get("name").toString())
                        i++
                        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
                        spinner.adapter = adapter
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                }
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)//sobrara cuando
            spinner.adapter = adapter
        }
    }


}
