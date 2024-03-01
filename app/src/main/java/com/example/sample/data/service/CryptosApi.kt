package com.example.sample.data.service


import com.example.sample.data.enitity.CryptoResponse

import retrofit2.Response
import retrofit2.http.GET

interface CryptosApi {

    @GET("v3/ac7d7699-a7f7-488b-9386-90d1a60c4a4b")
    suspend fun getCryptos(): Response<CryptoResponse>
}


