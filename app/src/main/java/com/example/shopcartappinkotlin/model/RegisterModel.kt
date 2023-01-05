package com.example.shopcartappinkotlin.model

data class RegisterModel(
    val fullName : String? = "",
    val address : String? = "",
    val mobileNumber : String? = "",
    val email : String? = "",
    val password : String? = ""
)
