package com.example.happyroutine.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.view.children
import androidx.fragment.app.FragmentTransaction

import com.example.happyroutine.R
import com.example.happyroutine.model.AppData
import kotlinx.android.synthetic.main.fragment_food_dont_want.*

/**
 * A simple [Fragment] subclass.
 */
class FoodDontWantFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_dont_want, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        for (food in AppData.foodList) {
            val foodCheckBox = CheckBox(this.context)
            foodCheckBox.text = food.name
            food_linear_layout.addView(foodCheckBox)
        }

        super.onViewCreated(view, savedInstanceState)
         save.setOnClickListener {
             fragmentManager?.let {
                it.beginTransaction().replace(R.id.frame_layout_navigation_bar,EditProfileFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }
    }


    private fun getCheckedFood(): List<String> {
        var checkedFood = mutableListOf<String>()
        for (x in 0..food_linear_layout.childCount) {
            val foodCheckBox = food_linear_layout.getChildAt(x)
            if(foodCheckBox is CheckBox && foodCheckBox.isChecked) {
                checkedFood.add(foodCheckBox.text.toString())
            }
        }
        return checkedFood
    }

}
