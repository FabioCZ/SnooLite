package com.gottlicher.snoolite.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gottlicher.snoolite.api.DataState
import com.gottlicher.snoolite.api.PostsDataFactory
import com.gottlicher.snoolite.api.RedditApiService
import com.gottlicher.snoolite.api.RedditPost

class HomeViewModel(val redditApiService: RedditApiService) : ViewModel() {

    lateinit var currentSub: LiveData<String>
    lateinit var postsLiveData: LiveData<PagedList<RedditPost>>
    lateinit var stateLiveData: LiveData<DataState>
    init {
        reInitLiveData()
    }
    private fun reInitLiveData() {
        val factory = PostsDataFactory(redditApiService, "all")

        val config = PagedList.Config.Builder ()
            .setEnablePlaceholders(false)
            .setPageSize(25)
            .setInitialLoadSizeHint(25)
            .build()
        postsLiveData = LivePagedListBuilder(factory, config).build()

        stateLiveData = Transformations.switchMap(factory.mutableLiveData) { it.stateLiveData }

    }

}