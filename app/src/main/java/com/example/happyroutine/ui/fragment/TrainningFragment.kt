package com.example.happyroutine.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.happyroutine.R
import kotlinx.android.synthetic.main.fragment_trainning.*


/**
 * A simple [Fragment] subclass.
 */
class TrainningFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_trainning, container, false)
        val spinner:Spinner=view.findViewById(R.id.recommended_spinner)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.recommended)
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinner.adapter = adapter

        val spinnerFavs:Spinner=view.findViewById(R.id.my_favs_spinner)
        val adapterFavs: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.myFavouritesTrainnings)
        )
        adapterFavs.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerFavs.adapter = adapterFavs

        val spinnerBeginner:Spinner=view.findViewById(R.id.beginner_spinner)
        val adapterBeginner: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.beginner)
        )
        adapterBeginner.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerBeginner.adapter = adapterBeginner

        val spinnerIntermediate:Spinner=view.findViewById(R.id.intermediate_spinner)
        val adapterIntermediate: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.intermediate)
        )
        adapterIntermediate.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerIntermediate.adapter = adapterIntermediate

        val spinnerAdvanced:Spinner=view.findViewById(R.id.advanced_spinner)
        val adapterAdvanced: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.advanced)
        )
        adapterAdvanced.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerAdvanced.adapter = adapterAdvanced

        val spinnerExpert:Spinner=view.findViewById(R.id.expert_spinner)
        val adapterExpert: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(R.array.expert)
        )
        adapterExpert.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinnerExpert.adapter = adapterExpert



        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_all_exercices.setOnClickListener {
            fragmentManager?.let {
                it.beginTransaction()
                    .replace(R.id.frame_layout_navigation_bar, ExercicesFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }
    }


}
