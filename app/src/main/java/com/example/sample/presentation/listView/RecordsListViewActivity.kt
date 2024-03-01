package com.example.sample.presentation.listView

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.example.sample.Application
import com.example.sample.R
import com.example.sample.databinding.ActivityMainBinding
import com.example.sample.domain.model.Crypto
import com.example.sample.domain.model.DataSource
import com.example.sample.domain.model.State
import com.example.sample.presentation.base.BaseActivity
import com.example.sample.utils.isNetworkConnectionAvailable
import com.example.sample.utils.isNotNull
import com.example.sample.utils.observe
import com.example.sample.utils.onQueryChange
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_main.*

class RecordsListViewActivity : BaseActivity<RecordsListViewModel, ActivityMainBinding>() {


    private lateinit var adapter: RecordsListAdaptor


    override fun getViewModelClass(): Class<RecordsListViewModel> {
        return RecordsListViewModel::class.java
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as Application).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        initializeMenu()
    }




    private fun initializeMenu() {
        val menuHost: MenuHost = this
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.main_menu, menu)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.filter -> {
                        showActionMenu()
                        true
                    }

                    else -> false
                }
            }
        }, this, Lifecycle.State.RESUMED)

    }


    private fun showActionMenu() {
        BottomSheetDialog(this).apply {
            setContentView(R.layout.action)
            behavior.maxWidth = ViewGroup.LayoutParams.MATCH_PARENT
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            setOnShowListener {
                val chipGroup = findViewById<ChipGroup>(R.id.first_row)
                viewModel.selectedChipIds.value?.let { selectedChips ->
                    for (chipId in selectedChips) {
                        chipGroup?.findViewById<Chip>(chipId)?.isChecked = true

                    }
                }
            }

            setOnDismissListener {
                val chipGroup = findViewById<ChipGroup>(R.id.first_row)
                viewModel.selectedChipIds.value = chipGroup?.checkedChipIds?.toSet()
                filterRecordsList(chipGroup?.checkedChipIds?.toSet()?: emptySet())
            }
            findViewById<View>(R.id.top_view)?.setOnClickListener { dismiss() }
            show()
        }
    }

    private fun filterRecordsList(selectedChipIds: Set<Int>) {
        val newList = mutableListOf<Crypto>()
        val oldList = (viewModel.recordsDownloadLivedata.value as? State.Success<List<Crypto>>)?.data ?: emptyList()
        if (selectedChipIds.isEmpty()) {
            adapter.setData(oldList)
            return
        }
        for (chipId in selectedChipIds) {
            when (chipId) {
                R.id.active_coins -> newList.addAll(oldList.filter { it.isActive })
                R.id.new_coins -> newList.addAll(oldList.filter { it.isNew })
                R.id.inactive_coins -> newList.addAll(oldList.filter { !it.isActive })
                R.id.only_tokens -> newList.addAll(oldList.filter { it.type == getString(R.string.token) })
                R.id.only_coins -> newList.addAll(oldList.filter { it.type == getString(R.string.h) })
            }
        }
        if(newList.isEmpty()){
            binding.recyclerView.visibility = View.GONE
            binding.tvNoRecordsFound.text = getString(R.string.selected_filters_dosn_t_have_any_cryptos)
            binding.tvNoRecordsFound.visibility = View.VISIBLE
        }else{
            binding.tvNoRecordsFound.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            adapter.setData(newList)
        }

    }



    override fun setupView() {
        setupSwipeRefreshLayout()
        observeViewModel()
        setListeners()
        setupAdapter()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            val source = determineDataSource()
            handleNetworkAvailability(source)
            viewModel.initiateDownload(true, source)
        }
    }


    private fun determineDataSource(): DataSource =
        if (applicationContext.isNetworkConnectionAvailable()) DataSource.DB_AND_NETWORK
        else DataSource.DB

    private fun handleNetworkAvailability(source: DataSource) {
        if (source == DataSource.DB) {
            showToast("No Network")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupAdapter() {
        adapter = RecordsListAdaptor { recordsList ->
            when {
                recordsList.isNullOrEmpty() -> showNoRecordsFound()
                recordsList.isNotNull() && recordsList.isNotEmpty() -> showRecordsFound()
            }
        }
        binding.recyclerView.adapter = adapter
    }

    private fun showNoRecordsFound() {
        with(binding) {
            tvNoRecordsFound.visibility = View.VISIBLE
            tvNoRecordsFound.text = getString(R.string.no_search_result_found)
            recyclerView.visibility = View.GONE
        }
    }

    private fun showRecordsFound() {
        with(binding) {
            tvNoRecordsFound.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }


    override fun inflateViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    private fun observeViewModel() {
        with(viewModel) {
            observe(recordsDownloadLivedata, ::onStateChange)
        }
    }


    private fun onStateChange(state: State<List<Crypto>>?) {
        state?.let {
            when (state) {
                is State.Failure -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                    binding.tvNoRecordsFound.visibility = View.VISIBLE
                    binding.tvNoRecordsFound.text = state.errorMessage
                }

                is State.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.searchView.visibility = View.GONE
                    binding.tvNoRecordsFound.visibility = View.GONE
                }

                is State.Success -> {
                    binding.tvNoRecordsFound.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                    binding.searchView.visibility = View.VISIBLE
                    adapter.setData(state.data)
                }
            }
        }
    }

    private fun setListeners() {
        binding.searchView.onQueryChange {
            adapter.filter.filter(it)
            true
        }
    }


}



