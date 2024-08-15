package com.example.testeapicatsj.model.remote

import com.example.testeapicatsj.model.data.CatData
import com.example.testeapicatsj.model.data.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("gallery/search/")
    suspend fun getImageCat(
        @Query("q")q:String
    ):Response<CatData>


}