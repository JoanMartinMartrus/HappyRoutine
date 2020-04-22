package com.example.happyroutine.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner

import com.example.happyroutine.R

/**
 * A simple [Fragment] subclass.
 */
class ExercicesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_exercices, container, false)
        val spinner: Spinner =view.findViewById(R.id.recommended_spinner)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.recommended_exercices)
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinner.adapter = adapter

        val spinnerFavs: Spinner =view.findViewById(R.id.my_favs_spinner)
        val adapterFavs: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.myFavouritesTrainnings_exercices)
        )
        adapterFavs.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerFavs.adapter = adapterFavs

        val spinnerBeginner: Spinner =view.findViewById(R.id.beginner_spinner)
        val adapterBeginner: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.beginner_exercices)
        )
        adapterBeginner.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerBeginner.adapter = adapterBeginner

        val spinnerIntermediate: Spinner =view.findViewById(R.id.intermediate_spinner)
        val adapterIntermediate: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.intermediate_exercices)
        )
        adapterIntermediate.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerIntermediate.adapter = adapterIntermediate

        val spinnerAdvanced: Spinner =view.findViewById(R.id.advanced_spinner)
        val adapterAdvanced: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.advanced_exercices)
        )
        adapterAdvanced.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerAdvanced.adapter = adapterAdvanced

        val spinnerExpert: Spinner =view.findViewById(R.id.expert_spinner)
        val adapterExpert: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.expert_exercices)
        )
        adapterExpert.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerExpert.adapter = adapterExpert



        // Inflate the layout for this fragment
        return view
    }


}
