package com.example.happyroutine.ui.activity

import android.os.Bundle

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.happyroutine.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_estadisticas_weight.*

class Estadisticas_weight : AppCompatActivity() {

    private val firebaseAuth: FirebaseAuth? = null
    private val firestore: FirebaseFirestore? = null
    private val weightEntries = ArrayList<Float>()
    //private val dateEntries = ArrayList<Timestamp>()
    //val users = db.collection("users")
    //if(currentUser.matches()){
    // val weightEntries = users.collection("weightTrack").document("weight")
    //weightEntries.set(NEW_WEIGHT)

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas_weight)

        weightEntries.add(75f)
        //weightEntries.add(75.5f)
        //weightEntries.add(76f)

        drawChart()
    }

    fun enterNewWeight(view: View){
        if(weightEntry.text.toString().isEmpty()){
            weightEntry.error = "Please, enter your weight"
            weightEntry.requestFocus()
            return
        }
        //val currentDate = Timestamp.now()
        //val newPosition = entries.size.toFloat() + 1
        val newWeight = weightEntry.text.toString().toFloat()
        //entries.add(Entry(newPosition, newWeight))
        weightEntries.add(newWeight)
        showLimits()
        drawChart()
    }

    private fun drawChart(){
        //Get weight and date lists from Firebase


        val entries = ArrayList<Entry>()
        var index = 1f
        for(value in weightEntries){
            entries.add(Entry(index, value))
            index += 1
        }

        //Assigns our list to LineDataSet and labels it
        val vl = LineDataSet(entries, "My Weight")

        //Drawing display configuration
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 5f
        vl.fillColor = R.color.colorPrimary
        vl.fillAlpha = R.color.colorBackgroundEnd

        //Set label rotation angle on X axis
        lineChart.xAxis.labelRotationAngle = 0f

        //Assign dataset to line chart in the layout to draw
        lineChart.data = LineData(vl)

        //Axis display configuration
        lineChart.axisRight.isEnabled = false

        lineChart.setVisibleXRangeMaximum(4f)
        //lineChart.moveViewToX(entries.size.toFloat()-3f)

        lineChart.moveViewTo(entries.size.toFloat(), entries.size.toFloat(), YAxis.AxisDependency.LEFT)
        val maximum = weightEntries.max() ?: 50f
        val minimum = weightEntries.min() ?: 0f
        lineChart.axisLeft.axisMaximum = maximum + 3.5f
        lineChart.axisLeft.axisMinimum = minimum - 3.5f

        //Enables zoom and scrolling through chart
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

        //Set labels
        lineChart.description.text = "Days"
        lineChart.setNoDataText("No entries yet!")

        //Sets animation when drawing the chart
        lineChart.animateX(1800, Easing.EaseInExpo)

        //Checking value points enabled
        val markerView = customChart(this, R.layout.chart_marker_view)
        lineChart.marker = markerView
    }

    private fun showLimits(){
        val overweight_value = 80f
        val overweight_limit = LimitLine(overweight_value, "Overweight")
        overweight_limit.lineWidth = 4f
        overweight_limit.enableDashedLine(10f,10f,0f)
        overweight_limit.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        overweight_limit.textSize = 10f
        //overweight_limit.lineColor = R.color.red

        val lowweight_value = 60f
        val lowweight_limit = LimitLine(lowweight_value, "Low weight")
        lowweight_limit.lineWidth = 4f
        lowweight_limit.enableDashedLine(10f,10f,0f)
        lowweight_limit.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        lowweight_limit.textSize = 10f
        //lowweight_limit.lineColor = R.color.wallet_holo_blue_light

        lineChart.axisLeft.addLimitLine(overweight_limit)
        lineChart.axisLeft.addLimitLine(lowweight_limit)
        return
    }
}