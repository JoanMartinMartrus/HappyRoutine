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

        customSpinner(R.id.recommended_spinner,R.array.recommended,view)
        customSpinner(R.id.my_favs_spinner,R.array.myFavouritesTrainnings,view)
        customSpinner(R.id.beginner_spinner,R.array.beginner,view)
        customSpinner(R.id.intermediate_spinner,R.array.intermediate,view)
        customSpinner(R.id.advanced_spinner,R.array.advanced,view)
        customSpinner(R.id.expert_spinner,R.array.expert,view)
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
