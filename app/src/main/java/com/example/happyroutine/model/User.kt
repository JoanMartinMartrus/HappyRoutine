package com.example.happyroutine.model

import com.example.happyroutine.model.enums.DietType
import com.example.happyroutine.model.enums.GenderType
import com.example.happyroutine.model.enums.ObjectiveType
import com.example.happyroutine.model.enums.OfferType
import com.google.firebase.Timestamp
import java.util.*

class User (var uid: String? = null,
            var name: String? = null,
            var surname: String? = null,
            var dateOfBirth: Timestamp? = null,
            var gender: GenderType? = null,
            var iconUserURL: String? = null,
            var email: String? = null,
            var platform: Boolean? = null,
            var offers: List<OfferType>? = null,
            var needs: List<OfferType>? = null,
            var objective: ObjectiveType? = null,
            var diet: List<DietType>? = null){



}