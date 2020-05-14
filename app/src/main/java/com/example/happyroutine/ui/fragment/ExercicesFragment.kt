package com.example.happyroutine.ui.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.FragmentTransaction

import com.example.happyroutine.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_exercices.*

/**
 * A simple [Fragment] subclass.
 */
class ExercicesFragment : Fragment() {

    lateinit var db : CollectionReference
   // var fullBody:ArrayList<String> = ArrayList()
   lateinit var recommended: ArrayAdapter<String>
    lateinit var favourites: ArrayAdapter<String>
    lateinit var back: ArrayAdapter<String>
    lateinit var abds: ArrayAdapter<String>
    lateinit var arms: ArrayAdapter<String>
    lateinit var legs: ArrayAdapter<String>
    lateinit var stretching: ArrayAdapter<String>
    lateinit var chest: ArrayAdapter<String>
    lateinit var buttocks: ArrayAdapter<String>
    lateinit var cardio: ArrayAdapter<String>
    lateinit var fullBody: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_exercices, container, false)

        db= FirebaseFirestore.getInstance().collection("exerciseOrStretching")

        paintScreen(view)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recommended_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowExerciceFragment(recommended.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        my_favs_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowExerciceFragment(favourites.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        Stretching.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowExerciceFragment(stretching.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        Abds.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowExerciceFragment(abds.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        Arms.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowExerciceFragment(arms.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        Legs.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowExerciceFragment(legs.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        Buttocks.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowExerciceFragment(buttocks.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        Chest.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowExerciceFragment(chest.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        Back.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowExerciceFragment(back.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        Cardio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowExerciceFragment(cardio.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        FullBody.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowExerciceFragment(fullBody.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
    }

    private fun getQueryWhere(id_spinner:Int, title_spinner:String, view:View, name:String, db: CollectionReference,adapter: ArrayAdapter<String>) {
        if(title_spinner.equals("Recommended")){ //TODO:PERSONALIZAR EL RECOMENDED
            val spinner: Spinner = view.findViewById(id_spinner)
            adapter.add(title_spinner)
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
            spinner.adapter = adapter
        }else if(title_spinner.equals("My Favourites")){
            val spinner: Spinner = view.findViewById(id_spinner)
            adapter.add(title_spinner)
            db.whereEqualTo("favourite", true).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        // fullBody.add(document.get("name").toString())
                        adapter.add(document.get("name").toString())
                        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
                        spinner.adapter = adapter
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                }
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)//sobrara cuando
            spinner.adapter = adapter
        }else {
            val spinner: Spinner = view.findViewById(id_spinner)
            adapter.add(title_spinner)
            db.whereEqualTo("muscles", name).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                       // fullBody.add(document.get("name").toString())
                        adapter.add(document.get("name").toString())
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

    fun paintScreen(view:View){
        recommended= ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.recommended_spinner,"Recommended",view,"Recommended",db,recommended)
        favourites= ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.my_favs_spinner,"My Favourites",view,"My Favourites",db,favourites)
        back= ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.Back,"Back",view,"BACK",db,back)
        abds = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.Abds,"Abds",view,"ABDS",db,abds)
        arms = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.Arms,"Arms",view,"ARMS",db,arms)
        legs= ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.Legs,"Legs",view,"LEGS",db,legs)
        chest= ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.Chest,"Chest",view,"CHEST",db,chest)
        stretching= ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.Stretching,"Stretching",view,"STRETCHING",db,stretching)
        buttocks= ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.Buttocks,"Buttocks",view,"BUTTOCKS",db,buttocks)
        cardio = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.Cardio,"Cardio",view,"CARDIO",db,cardio)
        fullBody = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.FullBody,"Full Body",view,"FULLBODY",db,fullBody)
    }


}
