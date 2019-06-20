package com.gottlicher.snoolite.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

enum class DataState { NONE, LOADING, LOADED, ERROR }

public class PostsDataSource (val redditApiService: RedditApiService, val subReddit: String) : PageKeyedDataSource<String, RedditPost> () {

    val TAG:String = "PostsDataSource"
    val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val stateLiveData: MutableLiveData<DataState> = MutableLiveData()


    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, RedditPost>) {
        Log.d(TAG, "Load initial")
        GlobalScope.launch(Dispatchers.IO) {
            try {
                stateLiveData.postValue(DataState.LOADING)
                val response = redditApiService.getPosts(subReddit, null, null)
                stateLiveData.postValue(DataState.LOADED)
                if (response.isSuccessful) {
                    val posts = response.body()!!.data.children.map { it.data }
                    callback.onResult(filterNsfw(posts), null, posts.last().name)
                } else {
                    Log.d(TAG, "Load initial error: ${response.code()}")
                    stateLiveData.postValue(DataState.ERROR)
                }
            } catch (e:Exception) {
                Log.d(TAG, "Load initial error: ${e.message}")
                stateLiveData.postValue(DataState.ERROR)
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        Log.d(TAG, "Load after ${params.key}")
        GlobalScope.launch(Dispatchers.IO) {
            try{
                stateLiveData.postValue(DataState.LOADING)
                val response = redditApiService.getPosts(subReddit, null, params.key)
                stateLiveData.postValue(DataState.LOADED)
                if (response.isSuccessful) {
                    val posts = response.body()!!.data.children.map { it.data }
                    callback.onResult(filterNsfw(posts), posts.last().name)
                } else {
                    Log.d(TAG, "Load after error: ${response.code()}")
                    stateLiveData.postValue(DataState.ERROR)
                }
            } catch (e:Exception) {
                Log.d(TAG, "Load after error: ${e.message}")
                stateLiveData.postValue(DataState.ERROR)
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        Log.d(TAG, "Load before ${params.key}")
        GlobalScope.launch(Dispatchers.IO) {
            try{
                stateLiveData.postValue(DataState.LOADING)
                val response = redditApiService.getPosts(subReddit, params.key, null)
                stateLiveData.postValue(DataState.LOADED)
                if (response.isSuccessful) {
                    val posts = response.body()!!.data.children.map { it.data }
                    callback.onResult(filterNsfw(posts),posts.first().name)
                } else {
                    stateLiveData.postValue(DataState.ERROR)
                }
            } catch (e:Exception) {
                stateLiveData.postValue(DataState.ERROR)
            }
        }
    }

    private fun filterNsfw(posts:List<RedditPost>): List<RedditPost> {
        return posts.filter { !it.over18 }
    }
}

public class PostsDataFactory (val redditApiService: RedditApiService, val subReddit: String) : DataSource.Factory<String, RedditPost> () {
    public val mutableLiveData = MutableLiveData<PostsDataSource> ()

    override fun create(): DataSource<String, RedditPost> {
        val source = PostsDataSource(redditApiService, subReddit)
        mutableLiveData.postValue(source)
        return source
    }
}