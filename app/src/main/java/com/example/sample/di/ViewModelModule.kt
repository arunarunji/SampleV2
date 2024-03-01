package com.example.sample.di

import androidx.lifecycle.ViewModel
import com.example.sample.presentation.listView.RecordsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
/**
 * Created by ArunKumar M on 29/02/2024.
 *
 */
@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(RecordsListViewModel::class)
    @IntoMap
    abstract fun  mainViewModel(recordsListViewModel: RecordsListViewModel):ViewModel

}