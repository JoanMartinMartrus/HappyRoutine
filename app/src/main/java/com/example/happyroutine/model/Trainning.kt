package com.example.happyroutine.model

class Trainning  (name: String, advice: String, favourite: Boolean, isDone: Boolean,
                  level: String, exercises: ArrayList<String>,objectives: String, id:String) {

    var name: String = ""
    var advice: String? = null
    var favourite: Boolean? = null
    var isDone: Boolean? = null
    var level: String? = null
    var exercises: ArrayList<String> = ArrayList()
    var objectives: String? = null
    var id:String=""

    init{
        this.name=name
        this.advice=advice
        this.favourite=favourite
        this.isDone=isDone
        this.level=level
        this.exercises=exercises
        this.objectives=objectives
        this.id=id
    }


}