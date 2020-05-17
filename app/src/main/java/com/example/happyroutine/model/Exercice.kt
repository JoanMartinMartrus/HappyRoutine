package com.example.happyroutine.model

class Exercice (name: String, description: String, favourite: Boolean, gifURL: String,
                level: String, muscles: String,objectives: String, id:String) {

    var name: String = ""
    var description: String? = null
    var favourite: Boolean? = null
    var gifURL: String? = null
    var level: String? = null
    var muscles: String? = null
    var objectives: String? = null
    var id:String=""

    init{
        this.name=name
        this.description=description
        this.favourite=favourite
        this.gifURL=gifURL
        this.level=level
        this.muscles=muscles
        this.objectives=objectives
        this.id=id
    }


}