package com.example.batmobile.DTOFromServer

data class Product(
    var sellerName: String,
    var productName: String,
    var picture: String,
    var description: String,
    var price: Double,
    var category: String,
    var measurement: String
)

data class ProductComment(
    var date: String?,  // Promenljivo u zavisnosti od vaše implementacije
    var text: String,
    var grade: Int,
    var name: String,
    var surname: String,
    var username: String,
    var picture: String
)

data class Seller(
    var name: String,
    var surname: String,
    var username: String,
    var email: String,
    var picture: String,
    var pib: String,
    var address: String,
    var longitude: Double,
    var latitude: Double
)

data class ProductViewResponse(
    var sellerDTO: Seller,
    var productDTO: Product,
    var productCommentList: List<ProductComment>
)




