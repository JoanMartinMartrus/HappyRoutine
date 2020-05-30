package com.example.happyroutine.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.happyroutine.R
import com.example.happyroutine.ui.activity.Navigation_bar_main
import com.example.happyroutine.ui.activity.customChart
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_estadisticas_weight.*
import java.text.SimpleDateFormat


/**
 * A simple [Fragment] subclass.
 */
class EstadisticasWeightFragment : Fragment() {

    private var weightEntries = ArrayList<Float>()
    private var dateEntries = ArrayList<Timestamp>()
    private val db = FirebaseFirestore.getInstance().collection("users")
    private val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity: Navigation_bar_main? = activity as Navigation_bar_main?
        activity?.hideNavBar()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estadisticas_weight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_ok.setOnClickListener { enterNewWeight(view) }
        showLimits()
        drawChart()
    }

    private fun enterNewWeight(view: View){
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

        weightEntry.text.clear()
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
                    var index = 0f

                    for(value: Float in weightEntries){
                        entries.add(Entry(index, value))
                        index += 1f
                    }

                    //Assigns the list to LineDataSet and labels it
                    val vl = LineDataSet(entries, "My Weight")

                    //Gets TimeStamps as strings and sets them as date labels
                    val format = SimpleDateFormat("dd/MM/yy")

                    lineChart.xAxis.valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            if(dateEntries.size == 1){
                                return format.format(dateEntries[0].toDate())
                            }
                            else{
                                return format.format(dateEntries[value.toInt()].toDate())
                            }
                        }
                    }
                    lineChart.xAxis.textSize = 8f

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
                    lineChart.axisLeft.axisMinimum = 0f
                    lineChart.xAxis.granularity = 1f

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

                                if(currentWeight > overweightValue - 10 && currentWeight < overweightValue + 10){
                                    lineChart.axisLeft.addLimitLine(overweightLimit)
                                }
                                if(currentWeight > lowWeightValue - 10 && currentWeight < lowWeightValue + 10){
                                    lineChart.axisLeft.addLimitLine(lowWeightLimit)
                                }
                            }
                        }
                }
            }
    }
}