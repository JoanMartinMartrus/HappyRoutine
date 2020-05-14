package com.example.happyroutine.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happyroutine.R
import com.example.happyroutine.model.AppData
import com.example.happyroutine.ui.activity.diet
import kotlinx.android.synthetic.main.fragment_diet.*

/**
 * A simple [Fragment] subclass.
 */
class DietFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fillRecommendedFood()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun fillRecommendedFood() {
        var textToShow = ""
        for (food in AppData.foodList) {
            textToShow = textToShow + food.name + System.getProperty("line.separator")
        }
        tv_recomended_food.text = textToShow
    }

}
