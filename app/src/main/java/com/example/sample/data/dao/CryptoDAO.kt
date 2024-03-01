package com.example.sample.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sample.data.enitity.CryptoDataEntity


/**
 * Created by ArunKumar M on 29/02/2024.
 *
 */
@Dao
interface CryptoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrypto(cryptos: List<CryptoDataEntity>)

    @Query("SELECT * FROM CryptoDataEntity")
    fun getCrypto() : List<CryptoDataEntity>

    @Query("DELETE FROM CryptoDataEntity")
    fun clearDataBase()

}