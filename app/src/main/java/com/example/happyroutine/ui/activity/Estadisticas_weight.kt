package com.example.happyroutine.ui.activity

import android.graphics.Color
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
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_estadisticas_weight.*
import kotlin.collections.ArrayList


class Estadisticas_weight : AppCompatActivity() {

    private var weightEntries = ArrayList<Float>()
    private var dateEntries = ArrayList<Timestamp>()
    private val db = FirebaseFirestore.getInstance().collection("users")
    private val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas_weight)
        showLimits()
        drawChart()
    }

    fun enterNewWeight(view: View){
        if(weightEntry.text.toString().isEmpty()){
            weightEntry.error = "Please, enter your weight"
            weightEntry.requestFocus()
            return
        }
        //Get new weight & date entries from the database
        db.document(uid).collection("weight").document("weight").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        weightEntries = document["weightEntries"] as ArrayList<Float>
                        dateEntries = document["dateEntries"] as ArrayList<Timestamp>
                    }
                }
            }

        //Add the new values and update the database
        dateEntries.add(Timestamp.now())
        weightEntries.add(weightEntry.text.toString().toFloat())

        db.document(uid).collection("weight").document("weight").update("weightEntries", weightEntries)
        db.document(uid).collection("weight").document("weight").update("dateEntries", dateEntries)

        //Refresh chart
        showLimits()
        drawChart()
    }

    private fun drawChart(){
        //Get weight and date lists from Firebase
        db.document(uid).collection("weight").document("weight").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        weightEntries = document["weightEntries"] as ArrayList<Float>
                        dateEntries = document["dateEntries"] as ArrayList<Timestamp>
                    }

                    //Sets the entries to be displayed in a list
                    val entries = ArrayList<Entry>()
                    var index = 1f

                    for(value: Float in weightEntries){
                        entries.add(Entry(index, value))
                        index += 1f
                    }

                    //Assigns the list to LineDataSet and labels it
                    val vl = LineDataSet(entries, "My Weight")

                    //Drawing display configuration
                    vl.setDrawValues(false)
                    vl.setDrawFilled(true)
                    vl.lineWidth = 5f
                    vl.fillColor = R.color.colorPrimary
                    vl.fillAlpha = R.color.colorBackgroundEnd

                    //Sets label rotation angle on X axis
                    lineChart.xAxis.labelRotationAngle = 0f

                    //Assigns dataset to line chart in the layout to draw
                    lineChart.data = LineData(vl)

                    //Axis display configuration
                    lineChart.axisRight.isEnabled = false

                    lineChart.setVisibleXRangeMaximum(4f)
                    lineChart.moveViewTo(entries.size.toFloat(), entries.size.toFloat(), YAxis.AxisDependency.LEFT)
                    val maximum = weightEntries.max() ?: 50f
                    val minimum = weightEntries.min() ?: 0f
                    lineChart.axisLeft.axisMaximum = maximum + 10f
                    lineChart.axisLeft.axisMinimum = minimum - 10f

                    //Enables zoom and scrolling through chart
                    lineChart.setTouchEnabled(true)
                    lineChart.setPinchZoom(true)

                    //Set labels
                    lineChart.description.text = "Days"
                    lineChart.setNoDataText("No entries yet!")

                    //Sets animation when drawing the chart
                    lineChart.animateX(1800, Easing.EaseInOutBack)

                    //Checking value points enabled
                    val markerView = customChart(this, R.layout.chart_marker_view)
                    lineChart.marker = markerView
                    lineChart.notifyDataSetChanged()
                    lineChart.invalidate()
                }
            }
    }

    private fun showLimits(){
        var height: Number = 1
        db.document(uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        height = document["height"] as Number
                        height = document["height"] as Number
                    }
                    var currentWeight = 1f
                    db.document(uid).collection("weight").document("weight").get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val document = task.result
                                if (document!!.exists()) {
                                    weightEntries = document["weightEntries"] as ArrayList<Float>
                                }
                                currentWeight =  weightEntries.last()

                                val currentHeight = height.toFloat()
                                val overweightValue = ((currentHeight/100)*(currentHeight/100)) * 25f
                                val overweightLimit = LimitLine(overweightValue, "Overweight")
                                overweightLimit.lineWidth = 4f
                                overweightLimit.enableDashedLine(10f,10f,0f)
                                overweightLimit.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
                                overweightLimit.textSize = 10f
                                overweightLimit.lineColor = Color.rgb(254,10,10)

                                val lowWeightValue = ((currentHeight/100)*(currentHeight/100)) * 18.4f
                                val lowWeightLimit = LimitLine(lowWeightValue, "Low weight")
                                lowWeightLimit.lineWidth = 4f
                                lowWeightLimit.enableDashedLine(10f,10f,0f)
                                lowWeightLimit.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
                                lowWeightLimit.textSize = 10f
                                lowWeightLimit.lineColor = Color.rgb(10, 10, 254)

                                if(currentWeight > overweightValue - 5 && currentWeight < overweightValue + 5){
                                    lineChart.axisLeft.addLimitLine(overweightLimit)
                                }
                                if(currentWeight > lowWeightValue - 5 && currentWeight < lowWeightValue + 5){
                                    lineChart.axisLeft.addLimitLine(lowWeightLimit)
                                }
                            }
                        }
                }
            }
    }
}