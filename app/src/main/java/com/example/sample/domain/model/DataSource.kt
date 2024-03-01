package com.example.sample.domain.model

sealed class DataSource {
        object DB : DataSource()
        object DB_AND_NETWORK : DataSource()
    }