package com.gottlicher.snoolite.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditApiService {

    @GET("r/{sub}/.json")
    suspend fun getPosts(@Path("sub") sub:String) : Response<RedditPostsResponse>

    @GET("{permalink}/.json")
    suspend fun getComments(@Path("permalink") postPermalink:String) : Response<String>
}
