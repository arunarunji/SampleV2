package com.example.sample.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sample.data.dao.CryptoDAO
import com.example.sample.data.enitity.CryptoDataEntity

/**
 * Created by ArunKumar M on 29/02/2024.
 *
 */
@Database(entities = [CryptoDataEntity::class], version = 1 ,  exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun getCryptoDAO() : CryptoDAO

}