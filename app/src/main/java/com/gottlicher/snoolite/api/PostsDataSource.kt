package com.gottlicher.snoolite.api

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

public class PostsDataSource (val redditApiService: RedditApiService, val subReddit: String) : PageKeyedDataSource<Int, RedditPost> () {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, RedditPost>) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = redditApiService.getPosts(subReddit)
            if (response.isSuccessful) {
                val posts = response.body()!!.data.children.map { it.data }
                callback.onResult(posts,null, 2)
            } else {
                //TODO error handling
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RedditPost>) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = redditApiService.getPosts(subReddit)
            if (response.isSuccessful) {
                val posts = response.body()!!.data.children.map { it.data }
                callback.onResult(posts,params.key + 1)
            } else {
                //TODO error handling
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RedditPost>) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = redditApiService.getPosts(subReddit)
            if (response.isSuccessful) {
                val posts = response.body()!!.data.children.map { it.data }
                callback.onResult(posts,params.key - 1)
            } else {
                //TODO error handling
            }
        }
    }

}

public class PostsDataFactory (val redditApiService: RedditApiService, val subReddit: String) : DataSource.Factory<Int, RedditPost> () {
    public val mutableLiveData = MutableLiveData<PostsDataSource> ()

    override fun create(): DataSource<Int, RedditPost> {
        val source = PostsDataSource(redditApiService, subReddit)
        mutableLiveData.postValue(source)
        return source
    }
}