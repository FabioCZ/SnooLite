package com.gottlicher.snoolite.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gottlicher.snoolite.api.PostsDataFactory
import com.gottlicher.snoolite.api.RedditApiService
import com.gottlicher.snoolite.api.RedditPost

class HomeViewModel(val redditApiService: RedditApiService) : ViewModel() {

    lateinit var currentSub: LiveData<String>
    lateinit var postsLiveData: LiveData<PagedList<RedditPost>>

    init {
        reInitLiveData()
    }
    private fun reInitLiveData() {
        val factory = PostsDataFactory(redditApiService, "all")

        postsLiveData = LivePagedListBuilder(factory, 20).build()

    }

}