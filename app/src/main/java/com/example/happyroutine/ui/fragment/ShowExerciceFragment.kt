package com.example.happyroutine.ui.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.happyroutine.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class ShowExerciceFragment (name: String) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View=inflater.inflate(R.layout.fragment_show_exercice, container, false)
        // Inflate the layout for this fragment
        return view
    }

    fun changeText(view: View,value: String,id_TextView:Int){
        val text: TextView =view.findViewById(id_TextView)
        text.text=value
    }
}
