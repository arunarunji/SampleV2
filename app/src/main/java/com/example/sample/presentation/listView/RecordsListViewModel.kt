package com.example.sample.presentation.listView

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sample.data.repositoryImpl.CryptoRepositoryImpl
import com.example.sample.domain.model.Crypto
import com.example.sample.domain.model.DataSource
import com.example.sample.domain.model.State
import com.example.sample.presentation.base.BaseViewModel
import com.example.sample.utils.isNetworkConnectionAvailable
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecordsListViewModel @Inject constructor(
    private val repository: CryptoRepositoryImpl,
    private val context : Context
) : BaseViewModel() {


    private val _recordsDownloadLivedata: MutableLiveData<State<List<Crypto>>> = MutableLiveData()
    val recordsDownloadLivedata: LiveData<State<List<Crypto>>>
        get() = _recordsDownloadLivedata


    val selectedChipIds = MutableLiveData<Set<Int>>(emptySet())

    init {
        initiateDownload(false , DataSource.DB_AND_NETWORK)
    }


    fun initiateDownload(isFromRefresh : Boolean ,source: DataSource) {
        if (!context.isNetworkConnectionAvailable()) {
            _recordsDownloadLivedata.value = State.Failure("No Network Connection , Pls Try again")
            return
        }
        fetchCryptos(isFromRefresh ,source)
    }

    private fun fetchCryptos(isFromRefresh : Boolean, source: DataSource) {
        viewModelScope.launch {
            if (!isFromRefresh) {
                _recordsDownloadLivedata.value = State.Loading()
            }
            else {
                selectedChipIds.value = emptySet()
            }
            val result =  repository.getCryptos(source)
            _recordsDownloadLivedata.value = result
        }
    }

}


