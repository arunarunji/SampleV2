package com.example.sample.domain.model



data class Crypto(
    val id : Int ,
    val name : String,
    val symbol : String,
    val isNew : Boolean,
    val isActive : Boolean,
    val type : String
)