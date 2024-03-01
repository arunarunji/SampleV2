package com.example.sample.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

fun Context.isNetworkConnectionAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}


fun Context.showToast(message: String, toastDuration: Int) {
     val toast = Toast.makeText(this, message, toastDuration)
     toast.show()
}