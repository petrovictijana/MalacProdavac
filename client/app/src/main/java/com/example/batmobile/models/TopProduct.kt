package com.example.batmobile.models

data class TopProduct(
    var productId: Int,
    var productName: String,
    var productPicture: String,
    var sellerUsername: String,
    var longitude: Double,
    var latitude: Double,
    var soldItems: Int
)
