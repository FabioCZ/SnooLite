package com.gottlicher.snoolite.di

import android.net.Uri
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.gottlicher.snoolite.api.RedditApiService
import com.gottlicher.snoolite.home.HomeViewModel
import com.gottlicher.snoolite.utils.UriTypeAdapter
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule  {
    fun create() : Module {
        return module {
            single { createOkHttp() }
            single { createGson() }
            single { createRedditApi(get(), get()) }
            viewModel { HomeViewModel(get()) }
        }
    }

    private fun createOkHttp() : OkHttpClient {
        return OkHttpClient()
    }

    private fun createGson() : Gson {
        return GsonBuilder()
            .registerTypeAdapter(object: TypeToken<Uri>(){}.type, UriTypeAdapter())
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    private fun createRedditApi(okhttp:OkHttpClient, gson:Gson) : RedditApiService {
        val retrofit = Retrofit.Builder()
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://reddit.com")
            .build();
        return retrofit.create(RedditApiService::class.java)
    }
}