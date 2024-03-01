package com.example.sample.di

import android.content.Context
import androidx.room.Room
import com.example.sample.R
import com.example.sample.data.DataBase

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
/**
 * Created by ArunKumar M on 29/02/2024.
 *
 */
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideCountryDB(context: Context): DataBase {
        return Room.databaseBuilder(context, DataBase::class.java, context.getString(R.string.crypto)).build()
    }
}