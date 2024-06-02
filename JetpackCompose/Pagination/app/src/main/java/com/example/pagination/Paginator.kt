package com.example.pagination

interface Paginator<Key, Item> {
    suspend fun loadNextList()
    fun reset()
}