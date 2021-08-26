package com.example.zenserapp.ui

import java.util.*

data class ListingModel(
    var id: Int = getAutoId(),
    var title: String ="",
    var price: Double =0.00,
    var condition: String ="",
    var description: String ="",
    var dealmethod: String ="",
    var category: String ="",
    var userid: Int = 0
        ){
    companion object{
        fun getAutoId():Int{
            val random = Random()
            return random.nextInt(100)
        }
    }
}