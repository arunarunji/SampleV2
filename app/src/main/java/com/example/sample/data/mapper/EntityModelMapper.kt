package com.example.sample.data.mapper

import com.example.sample.domain.model.Crypto

/**
 * Created by ArunKumar M on 29/02/2024.
 *
 */
interface EntityModelMapper<T, U> {
    fun fromEntity(entity: T): U
    fun toEntity(model: U): T
}

interface CryptoEntityMapper<T> : EntityModelMapper<T, Crypto>

