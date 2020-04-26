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

        customSpinner(R.id.recommended_spinner,R.array.recommended_exercices,view)
        customSpinner(R.id.my_favs_spinner,R.array.myFavouritesTrainnings_exercices,view)
        customSpinner(R.id.Abds,R.array.Abds,view)
        customSpinner(R.id.Arms,R.array.Arms,view)
        customSpinner(R.id.Legs,R.array.Legs,view)
        customSpinner(R.id.Chest,R.array.Chest,view)
        customSpinner(R.id.Back,R.array.Back,view)
        customSpinner(R.id.Buttocks,R.array.Buttocks,view)
        customSpinner(R.id.Stretching,R.array.Stretching,view)
        customSpinner(R.id.Cardio,R.array.Cardio,view)
        customSpinner(R.id.FullBody,R.array.FullBody,view)
        // Inflate the layout for this fragment
        return view
    }
    private fun customSpinner(id_spinner:Int, id_entries:Int, view:View){
        val spinner:Spinner=view.findViewById(id_spinner)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            R.layout.custom_spinner,
            resources.getStringArray(id_entries)
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
        spinner.adapter = adapter

    }


}
