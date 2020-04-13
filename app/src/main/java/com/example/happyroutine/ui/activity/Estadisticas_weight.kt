package com.example.happyroutine.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.happyroutine.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_estadisticas_weight.*


class Estadisticas_weight : AppCompatActivity() {

    //lateinit var auth: FirebaseAuth
    private var weightEntries = ArrayList<Float>()
    private var dateEntries = ArrayList<Timestamp>()

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas_weight)
        //val userUid = FirebaseAuth.getInstance().currentUser?.uid

        weightEntries.add(75f)
        weightEntries.add(75.5f)
        weightEntries.add(76f)

        drawChart()
    }

    fun enterNewWeight(view: View){
        if(weightEntry.text.toString().isEmpty()){
            weightEntry.error = "Please, enter your weight"
            weightEntry.requestFocus()
            return
        }
        //Get new weight & date entries and update the database
        val db = FirebaseFirestore.getInstance().collection("users")
        db.document(FirebaseAuth.getInstance().currentUser!!.uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        weightEntries = document["weight"] as ArrayList<Float>
                        dateEntries = document["date"] as ArrayList<Timestamp>
                    }
                }
            }
        val newWeight = weightEntry.text.toString().toFloat()
        val newDate = Timestamp.now()
        weightEntries.add(newWeight)
        dateEntries.add(newDate)

        db.document(FirebaseAuth.getInstance().currentUser!!.uid)
            .set(weightEntries, SetOptions.merge())
            .addOnSuccessListener {
                Log.i("DB", "Data stored succesfully")
            }
            .addOnFailureListener {e ->
                Log.i("DB", "Something went wrong",e)
                Toast.makeText(baseContext, "Something went wrong$e",
                    Toast.LENGTH_LONG).show()
            }

        db.document(FirebaseAuth.getInstance().currentUser!!.uid)
            .set(dateEntries, SetOptions.merge())
            .addOnSuccessListener {
                Log.i("DB", "Data stored succesfully")
            }
            .addOnFailureListener {e ->
                Log.i("DB", "Something went wrong",e)
                Toast.makeText(baseContext, "Something went wrong$e",
                    Toast.LENGTH_LONG).show()
            }

        /*val weightTrack = hashMapOf(
            "weightEntries" to weightEntries,
            "dateEntries" to dateEntries
        )*/
        //Refresh chart
        showLimits()
        drawChart()
    }

    private fun drawChart(){
        //Get weight and date lists from Firebase
        val db = FirebaseFirestore.getInstance().collection("users")
        db.document(FirebaseAuth.getInstance().currentUser!!.uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        weightEntries = document["weight"] as ArrayList<Float>
                        dateEntries = document["date"] as ArrayList<Timestamp>
                    }
                }
            }

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
        var currentHeight = 0f
        val db = FirebaseFirestore.getInstance().collection("users")
        db.document(FirebaseAuth.getInstance().currentUser!!.uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        currentHeight = document["height"] as Float
                    }
                }
            }
        val overweightValue = (currentHeight/100) * (currentHeight/100) * 25f
        val overweightLimit = LimitLine(overweightValue, "Overweight")
        overweightLimit.lineWidth = 4f
        overweightLimit.enableDashedLine(10f,10f,0f)
        overweightLimit.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        overweightLimit.textSize = 10f
        overweightLimit.lineColor = Color.rgb(254,10,10)

        val lowweightValue = (currentHeight/100) * (currentHeight/100) * 18.4f
        val lowweightLimit = LimitLine(lowweightValue, "Low weight")
        lowweightLimit.lineWidth = 4f
        lowweightLimit.enableDashedLine(10f,10f,0f)
        lowweightLimit.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        lowweightLimit.textSize = 10f
        lowweightLimit.lineColor = Color.rgb(10, 10, 254)

        lineChart.axisLeft.addLimitLine(overweightLimit)
        lineChart.axisLeft.addLimitLine(lowweightLimit)
        return
    }
}