package com.example.happyroutine.ui.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.happyroutine.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_trainning.*


/**
 * A simple [Fragment] subclass.
 */
class TrainningFragment : Fragment() {
    lateinit var db : CollectionReference
    // var fullBody:ArrayList<String> = ArrayList()
    lateinit var recommended: ArrayAdapter<String>
    lateinit var favourites: ArrayAdapter<String>
    lateinit var beginner: ArrayAdapter<String>
    lateinit var intermediate: ArrayAdapter<String>
    lateinit var advanced: ArrayAdapter<String>
    lateinit var expert: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_trainning, container, false)


        db= FirebaseFirestore.getInstance().collection("Trainning")

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
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowTrainningFragment(recommended.getItem(position).toString()))
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
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowTrainningFragment(favourites.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        beginner_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowTrainningFragment(beginner.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        intermediate_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowTrainningFragment(intermediate.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        advanced_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowTrainningFragment(advanced.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }
        expert_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0){
                    var value:String= expert.getItem(position).toString()
                    fragmentManager?.let {
                        it.beginTransaction().replace(R.id.frame_layout_navigation_bar,ShowTrainningFragment(expert.getItem(position).toString()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit()
                    }
                }
            }
        }


    }

    private fun getQueryWhere(id_spinner:Int, title_spinner:String, view:View, level:String, db: CollectionReference,adapter: ArrayAdapter<String>) {
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
            db.whereEqualTo("level", level).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
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
        beginner= ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.beginner_spinner,"Beginner",view,"BEGINNER",db,beginner)
        intermediate = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.intermediate_spinner,"Intermediate",view,"INTERMEDIATE",db,intermediate)
        advanced = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.advanced_spinner,"Advanced",view,"ADVANCED",db,advanced)
        expert= ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner
        )
        getQueryWhere(R.id.expert_spinner,"Expert",view,"EXPERT",db,expert)
    }


}
