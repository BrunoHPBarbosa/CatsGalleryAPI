package com.example.testeapicatsj.model.remote

import com.example.testeapicatsj.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val constRequest = chain.request().newBuilder()

        val request = constRequest.addHeader(
            "Authorization","${Constants.AUTHORIZATION}"
        ).build()



        return chain.proceed(request)
    }

}
