package com.example.sample.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.sample.utils.factory.ViewModelFactory
import javax.inject.Inject


abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {

  
    protected lateinit var viewModel: VM
    protected abstract fun getViewModelClass(): Class<VM>

    private var _binding: VB? = null
    val binding get() = _binding!!


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        _binding = inflateViewBinding()
        setContentView(binding.root)
        setupView()
        observeData()
    }



    private fun init() {
        viewModel = ViewModelProvider(this, viewModelFactory)[getViewModelClass()]
    }


    protected abstract fun setupView()

    protected open fun observeData() {

    }

    abstract fun inflateViewBinding(): VB

}