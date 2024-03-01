package com.example.sample.data.enitity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName




/**
 * Created by ArunKumar M on 29/02/2024.
 *
 */
@Entity
data class CryptoDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @SerializedName("name")
    val name : String,
    @SerializedName("symbol")
    val symbol : String,
    @SerializedName("is_new")
    val isNew : Boolean,
    @SerializedName("is_active")
    val isActive : Boolean,
    @SerializedName("type")
    val type : String
)





//    "cryptocurrencies": [
//    {
//        "name": "Bitcoin",
//        "symbol": "BTC",
//        "is_new": false,
//        "is_active": true,
//        "type": "coin"
//    },

