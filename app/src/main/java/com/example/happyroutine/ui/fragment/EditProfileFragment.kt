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
import com.example.happyroutine.ui.activity.user_information
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_estadisticas_main.*
import kotlinx.android.synthetic.main.fragment_food_dont_want.*

/**
 * A simple [Fragment] subclass.
 */
class EditProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_profile_f.setOnClickListener {
            activity?.let {
                val intent = Intent(it, user_information::class.java)
                it.startActivity(intent)
            }
        }
        edit_food.setOnClickListener {
            fragmentManager?.let {
                it.beginTransaction().replace(R.id.frame_layout_navigation_bar,FoodDontWantFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            }
        }
    }
}
