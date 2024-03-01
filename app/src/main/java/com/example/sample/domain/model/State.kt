package com.example.sample.domain.model

import com.example.sample.utils.Constants.EMPTY


/**
 * Created by ArunKumar M on 29/02/2024.
 *
 */
sealed class State<T> {

    data class Success<T>(val data: T) : State<T>()

    data class Failure<T>(val errorMessage: String = EMPTY, val errorData: T? = null) : State<T>()

    data class Loading<T>(val isLoading: Boolean = false) : State<T>()
}