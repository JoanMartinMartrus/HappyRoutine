package com.example.happyroutine.ui.fragment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.happyroutine.R
import com.example.happyroutine.ui.activity.Navigation_bar_main
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_estadisticas_trainning_days.*
import java.time.LocalDateTime
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

/**
 * A simple [Fragment] subclass.
 */
class EstadisticasTrainningDaysFragment : Fragment() {

    lateinit var db : CollectionReference
    lateinit var data : CollectionReference
    var array: ArrayList<String> = arrayListOf()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View=inflater.inflate(R.layout.fragment_estadisticas_trainning_days, container, false)
        db= FirebaseFirestore.getInstance().collection("Trainning")
        data= FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).collection("trainningData")
        val activity: Navigation_bar_main? = activity as Navigation_bar_main?
        activity?.hideNavBar()
        val localDate:LocalDateTime= LocalDateTime.now()
        val date:String=localDate.dayOfMonth.toString()+"/"+localDate.monthValue.toString()+"/"+localDate.year.toString()
        changeText(view,localDate.dayOfMonth,localDate.monthValue,localDate.year,date,R.id.fecha)
        // Inflate the layout for this fragment
        return view
    }
    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView.setOnDateChangeListener({ view1, year, month, dayOfMonth ->
            val date:String=dayOfMonth.toString()+"/"+(month+1).toString()+"/"+year.absoluteValue.toString()
            changeText(view,dayOfMonth,(month+1),year,date,R.id.fecha)
        })
        listView.setOnItemClickListener { parent, view, position, id ->
            if (!array.get(position).equals("Not data to show")) {
                fragmentManager?.let {
                    it.beginTransaction().replace(
                        R.id.frame_layout_navigation_bar,
                        ShowTrainningFragment(array.get(position))
                    )
                        .addToBackStack("statisticsTrainning")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                }
            }
        }

    }

    fun changeText(view: View,day:Int,month: Int,year:Int,value:String,id_TextView:Int){
        array.clear()
        //array.add("Not data to show")
        val text: TextView =view.findViewById(id_TextView)
        text.text=value
        val listView:ListView=view.findViewById(R.id.listView)
        data.whereEqualTo("day", day).whereEqualTo("month",month).whereEqualTo("year",year).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    println( document.get("name").toString())
                    array.add( document.get("name").toString())
                    val adapter= ArrayAdapter(view.context, android.R.layout.simple_list_item_1, array)
                    listView.adapter = adapter
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        val adapter= ArrayAdapter(view.context, android.R.layout.simple_list_item_1, array)
        listView.adapter = adapter
    }
}
