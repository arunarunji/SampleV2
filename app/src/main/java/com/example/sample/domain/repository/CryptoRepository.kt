package com.example.sample.domain.repository

import com.example.sample.domain.model.Crypto
import com.example.sample.domain.model.DataSource
import com.example.sample.domain.model.State

interface CryptoRepository {
    suspend fun getCryptos(source : DataSource) : State<List<Crypto>>
}