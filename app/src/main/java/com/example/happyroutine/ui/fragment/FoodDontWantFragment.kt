package com.example.happyroutine.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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

        super.onViewCreated(view, savedInstanceState)
         save.setOnClickListener {
             AppData.updateFoodList(getCheckedFood(), getUncheckedFood())
             for (food in AppData.dontWantFood) {
                 Log.i("FOOOOOOODDONT----->", food.name)
             }
             for (food in AppData.foodList) {
                 Log.i("FOOOOOOODYES----->", food.name)
             }
             fragmentManager?.let {
                 it.beginTransaction().replace(R.id.frame_layout_navigation_bar,EditProfileFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }
    }



    override fun onResume() {
        for (food in AppData.foodList) {
            val foodCheckBox = CheckBox(this.context)
            foodCheckBox.text = food.name
            food_linear_layout.addView(foodCheckBox)
        }

        for (food in AppData.dontWantFood) {
            val foodCheckBox = CheckBox(this.context)
            foodCheckBox.text = food.name
            foodCheckBox.isChecked = true
            food_linear_layout.addView(foodCheckBox)
        }
        super.onResume()
    }

    private fun getUncheckedFood(): List<String> {
        var uncheckedFood = mutableListOf<String>()
        for (x in 0..food_linear_layout.childCount) {
            val foodCheckBox = food_linear_layout.getChildAt(x)
            if(foodCheckBox is CheckBox && !foodCheckBox.isChecked) {
                uncheckedFood.add(foodCheckBox.text.toString())
            }
        }
        return uncheckedFood
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
