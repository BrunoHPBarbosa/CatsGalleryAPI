package com.example.testeapicatsj.model.remote

import com.example.testeapicatsj.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Client {

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .addConverterFactory( GsonConverterFactory.create() )
            .client(
                OkHttpClient.Builder()
                    .addInterceptor( ApiInterceptor() )
                    .build()
            )
            .build()
            .create( ApiService::class.java )
    }

}