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

        val spinnerFavs:Spinner=view.findViewById(R.id.my_favs_spinner)
        val adapterFavs: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.myFavouritesTrainnings_exercices)
        )
        adapterFavs.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerFavs.adapter = adapterFavs

        val spinnerAbds: Spinner =view.findViewById(R.id.Abds)
        val adapterAbds: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.Abds)
        )
        adapterAbds.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerAbds.adapter = adapterAbds

        val spinnerArms: Spinner =view.findViewById(R.id.Arms)
        val adapterArms: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.Arms)
        )
        adapterArms.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerArms.adapter = adapterArms

        val spinnerLegs: Spinner =view.findViewById(R.id.Legs)
        val adapterLegs: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.Legs)
        )
        adapterLegs.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerLegs.adapter = adapterLegs

        val spinnerChest: Spinner =view.findViewById(R.id.Chest)
        val adapterChest: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.Chest)
        )
        adapterChest.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerChest.adapter = adapterChest

        val spinnerBack: Spinner =view.findViewById(R.id.Back)
        val adapterBack: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.Back)
        )
        adapterBack.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerBack.adapter = adapterBack

        val spinnerButtocks: Spinner =view.findViewById(R.id.Buttocks)
        val adapterButtocks: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.Buttocks)
        )
        adapterButtocks.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerButtocks.adapter = adapterButtocks

        val spinnerStretching: Spinner =view.findViewById(R.id.Stretching)
        val adapterStretching: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.Stretching)
        )
        adapterStretching.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerStretching.adapter = adapterStretching

        val spinnerCardio: Spinner =view.findViewById(R.id.Cardio)
        val adapterCardio: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.Cardio)
        )
        adapterCardio.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerCardio.adapter = adapterCardio

        val spinnerFullBody: Spinner =view.findViewById(R.id.FullBody)
        val adapterFullBody: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.FullBody)
        )
        adapterFullBody.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerFullBody.adapter = adapterFullBody

        // Inflate the layout for this fragment
        return view
    }


}
