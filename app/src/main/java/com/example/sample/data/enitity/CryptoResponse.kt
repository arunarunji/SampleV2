package com.example.sample.data.enitity

import com.google.gson.annotations.SerializedName

/**
 * Created by ArunKumar M on 29/02/2024.
 *
 */
data class CryptoResponse(
    @SerializedName("cryptocurrencies")
    val cryptocurrencies: List<CryptoDataEntity>
)

//    "cryptocurrencies": [
//    {
//        "name": "Bitcoin",
//        "symbol": "BTC",
//        "is_new": false,
//        "is_active": true,
//        "type": "coin"
//    },