package com.example.happyroutine.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup

import com.example.happyroutine.R
import com.example.happyroutine.ui.activity.customChart
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_estadisticas_weight.*

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
        // Inflate the layout for this fragment
        //drawChart()
        return inflater.inflate(R.layout.fragment_estadisticas_weight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_ok.setOnClickListener {
            enterNewWeight()
        }
    }

    fun enterNewWeight(){
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
        //showLimits()
        //drawChart()
    }


}
