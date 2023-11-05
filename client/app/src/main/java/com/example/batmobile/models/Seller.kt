package com.example.batmobile.models

data class Seller(
    var id: Int,
    var name: String,
    var surname: String,
    var username: String,
    var longitude: Double,
    var latitude: Double,
    var numberOfOrders: Int
)
