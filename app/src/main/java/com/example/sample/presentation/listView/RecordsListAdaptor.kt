package com.example.sample.presentation.listView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.databinding.RecordItemBinding
import com.example.sample.domain.model.Crypto
import com.example.sample.utils.searchable

class RecordsListAdaptor(
    val onSearchCompleted: (List<Crypto>?) -> Unit
) : RecyclerView.Adapter<RecordsListAdaptor.ViewHolder>(), Filterable {


    inner class ViewHolder(private val recordsItems: RecordItemBinding) :
        RecyclerView.ViewHolder(recordsItems.root) {

        fun bind(crypto: Crypto) {
            with(recordsItems) {
                imNew.visibility = if (crypto.isNew) View.VISIBLE else View.GONE
                imCoin.setImageResource(
                    when {
                        crypto.isActive -> R.drawable.coin_active
                        crypto.type == "token" -> R.drawable.token
                        else -> R.drawable.coin_inactive
                    }
                )
                tvName.text = crypto.name
                tvSymbol.text = crypto.symbol
            }
        }
    }

    private var filterList = mutableListOf<Crypto>()

    private var originalData = mutableListOf<Crypto>()
    fun setData(records: List<Crypto>) {

        filterList.clear()
        filterList.addAll(records)
        originalData.addAll(records)
        updateData()
    }

    fun updateData() {
        val diffUtil = MyDiffUtil(originalData, filterList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        diffResults.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecordItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filterList[position])
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(seachWord: CharSequence?): FilterResults {
                val search = seachWord.toString().searchable()
                val filterData = mutableSetOf<Crypto>().apply {
                    if (seachWord.isNullOrEmpty()) {
                        addAll(originalData)
                    } else {

                        addAll(originalData.filter { it.name.searchable().startsWith(search) })
                        addAll(originalData.filter { it.name.searchable().contains(search, ignoreCase = true) })
                    }
                }
                return FilterResults().apply {
                    count = filterData.size
                    values = filterData.toList()
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = (results?.values as? List<Crypto> ?: originalData).toMutableList()
                onSearchCompleted(filterList)
                if (!filterList.isNullOrEmpty()) {
                    updateData()
                }
            }
        }
    }
}
