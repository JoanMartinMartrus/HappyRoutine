package com.example.happyroutine.model

import com.example.happyroutine.model.enums.DietType
import com.example.happyroutine.model.enums.GenderType
import com.example.happyroutine.model.enums.ObjectiveType
import com.example.happyroutine.model.enums.OfferType
import java.util.*

class User (username: String, name: String, surname: String, dateOfBirth: Date, gender: GenderType,
            iconUserURL: String, email: String, password: String, platform: Boolean, offer: OfferType,
            objectives: ObjectiveType, diet: DietType){

    var username: String? = null
    var name: String? = null
    var surname: String? = null
    var dateOfBirth: Date? = null
    var gender: GenderType? = null
    var iconUserURL: String? = null
    var email: String? = null
    var password: String? = null
    var platform: Boolean? = null
    var offer: OfferType? = null
    var objectives: ObjectiveType? = null
    var diet: DietType? = null


    init{
        this.username=username
        this.name=name
        this.surname=surname
        this.dateOfBirth=dateOfBirth
        this.gender=gender
        this.iconUserURL=iconUserURL
        this.email=email
        this.password=password
        this.platform=platform
        this.offer=offer
        this.objectives=objectives
        this.diet=diet
    }

}