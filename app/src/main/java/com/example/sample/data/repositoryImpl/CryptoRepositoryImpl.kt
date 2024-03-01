package com.example.sample.data.repositoryImpl
import com.example.sample.data.DataBase
import com.example.sample.data.enitity.CryptoDataEntity
import com.example.sample.data.mapper.CryptosEntityMapperImpl
import com.example.sample.domain.model.Crypto
import com.example.sample.domain.repository.CryptoRepository
import com.example.sample.data.service.CryptosApi
import com.example.sample.domain.model.DataSource
import com.example.sample.domain.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject


/**
 * Created by ArunKumar M on 29/02/2024.
 *
 */
class CryptoRepositoryImpl @Inject constructor(
    private val cryptoApi: CryptosApi,
    private val cryptoDAO: DataBase
)  : CryptoRepository

{
    override suspend fun getCryptos( source  : DataSource): State<List<Crypto>> {
        return withContext(Dispatchers.IO) {

            when (source) {
                DataSource.DB -> {
                    fetchCryptoFromLocalDb()
                }
                DataSource.DB_AND_NETWORK -> {
                    try {
                        val response = cryptoApi.getCryptos()
                        if (response.isSuccessful) {
                            response.body()?.cryptocurrencies?.let {
                                return@withContext handleSuccessResponse(it)
                            } ?: handleFailureResponse("Response body is null.")
                        } else {
                            handleFailureResponse(response.errorBody()?.string() ?: "Unknown error")
                        }
                    } catch (e: Exception) {
                        handleFailureResponse("Network request failed: ${e.message}")
                    }
                }
            }

        }
    }


    private fun fetchCryptoFromLocalDb(): State<List<Crypto>> {
        val cryptoList = cryptoDAO.getCryptoDAO().getCrypto()

        return if (cryptoList.isEmpty()) {
            handleFailureResponse("Coins not available, Please try again")
        } else {
            State.Success(cryptoList.map { CryptosEntityMapperImpl.fromEntity(it) })
        }
    }


    private suspend fun handleSuccessResponse(body: List<CryptoDataEntity> ) :State<List<Crypto>>{
        cryptoDAO.getCryptoDAO().clearDataBase()
        cryptoDAO.getCryptoDAO().insertCrypto(body)
        return fetchCryptoFromLocalDb()
    }


    private fun handleFailureResponse(errorMessage:String) : State<List<Crypto>>{
        return State.Failure(errorMessage)
    }
}


