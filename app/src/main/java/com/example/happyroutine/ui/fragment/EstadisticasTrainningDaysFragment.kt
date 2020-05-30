package com.example.happyroutine.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.happyroutine.R
import com.example.happyroutine.ui.activity.Navigation_bar_main
import kotlinx.android.synthetic.main.fragment_estadisticas_trainning_days.*
import java.time.LocalDateTime
import java.util.*
import kotlin.math.absoluteValue

/**
 * A simple [Fragment] subclass.
 */
class EstadisticasTrainningDaysFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity: Navigation_bar_main? = activity as Navigation_bar_main?
        activity?.hideNavBar()
        var view:View=inflater.inflate(R.layout.fragment_estadisticas_trainning_days, container, false)
        val localDate:LocalDateTime= LocalDateTime.now()
        val date:String=localDate.dayOfMonth.toString()+"/"+localDate.monthValue.toString()+"/"+localDate.year.toString()
        changeText(view,date,R.id.fecha)
        // Inflate the layout for this fragment
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView.setOnDateChangeListener({ view1, year, month, dayOfMonth ->
            val date:String=dayOfMonth.toString()+"/"+(month+1).toString()+"/"+year.absoluteValue.toString()
            changeText(view,date,R.id.fecha)
        })

    }

    fun changeText(view: View,value: String,id_TextView:Int){
        val text: TextView =view.findViewById(id_TextView)
        text.text=value
        val listView:ListView=view.findViewById(R.id.listView)
        val array= arrayOfNulls<String>(1)
        array[0]=("Not data to show")

        val adapter= ArrayAdapter(view.context, android.R.layout.simple_list_item_1, array)
        listView.adapter = adapter
    }
}
