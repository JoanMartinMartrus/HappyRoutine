package com.example.happyroutine.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.happyroutine.R
import com.example.happyroutine.ui.activity.Navigation_bar_main
import kotlinx.android.synthetic.main.activity_navigation_bar_main.*
import kotlinx.android.synthetic.main.fragment_estadisticas_main.*

/**
 * A simple [Fragment] subclass.
 */
class EstadisticasMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity: Navigation_bar_main? = activity as Navigation_bar_main?
        activity?.showNavBar()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estadisticas_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_weight.setOnClickListener{
            fragmentManager?.let {
                it.beginTransaction().replace(R.id.frame_layout_navigation_bar,EstadisticasWeightFragment())
                    .addToBackStack("statistics")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }
        button_trainning_days.setOnClickListener{
            fragmentManager?.let {
                it.beginTransaction().replace(R.id.frame_layout_navigation_bar,EstadisticasTrainningDaysFragment())
                    .addToBackStack("statistics")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }
    }
}
