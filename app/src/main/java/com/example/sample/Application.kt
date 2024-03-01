package com.example.sample

import android.app.Application
import com.example.sample.di.ApplicationComponent
import com.example.sample.di.DaggerApplicationComponent

/**
 * Created by ArunKumar M on 29/02/2024.
 *
 */
class Application : Application() {

    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}