package com.example.sample.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <Type : Any, L : LiveData<Type>> LifecycleOwner.observe(liveData: L, observer: (Type?) -> Unit) =
    liveData.observe(this, Observer(observer))