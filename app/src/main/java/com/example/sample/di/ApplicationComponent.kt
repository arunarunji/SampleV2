package com.example.sample.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.sample.presentation.listView.RecordsListViewActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by ArunKumar M on 29/02/2024.
 *
 */
@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class,ViewModelModule::class])
interface ApplicationComponent {

    fun inject(recordsListViewActivity: RecordsListViewActivity)

    fun getMap(): Map<Class<*>, ViewModel>

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

}