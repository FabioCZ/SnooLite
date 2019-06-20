package com.gottlicher.snoolite.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApiService {

    @GET("r/{sub}/.json?rawJson=1")
    suspend fun getPosts(@Path("sub") sub:String, @Query("before") before:String?, @Query ("after") after:String?) : Response<RedditDataWrapper<RedditPost>>

    @GET("{permalink}/.json?rawJson=1&depth=1")
    suspend fun getPermalink(@Path("permalink", encoded = true) postPermalink:String) : Response<RedditPermalink>
}
