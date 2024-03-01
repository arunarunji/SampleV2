package com.example.sample.utils

import androidx.appcompat.widget.SearchView
import java.util.Locale


fun String.searchable(): String = trim().toLowerCase(Locale.ROOT)

fun Any?.isNotNull(): Boolean = this != null

fun SearchView.onQueryChange(action: (query: String) -> Boolean) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return action(query.toString())
        }
    })
}