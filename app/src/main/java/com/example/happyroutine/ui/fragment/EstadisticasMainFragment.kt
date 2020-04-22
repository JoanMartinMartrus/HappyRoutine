package com.example.happyroutine.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.happyroutine.R
import com.example.happyroutine.ui.activity.Estadisticas_weight
import kotlinx.android.synthetic.main.fragment_estadisticas_main.*

/**
 * A simple [Fragment] subclass.
 */
class EstadisticasMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estadisticas_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_weight.setOnClickListener {
            activity?.let {
                val intent = Intent(it, Estadisticas_weight::class.java)
                it.startActivity(intent)
            }
        }
        /*button_weight.setOnClickListener{
            fragmentManager?.let {
                it.beginTransaction().replace(R.id.frame_layout_navigation_bar,EstadisticasWeightFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }*/
        button_trainning_days.setOnClickListener{
            fragmentManager?.let {
                it.beginTransaction().replace(R.id.frame_layout_navigation_bar,EstadisticasTrainningDaysFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }
    }
}
