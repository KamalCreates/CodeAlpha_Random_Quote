package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface{
    @GET("random")
    fun getData(): Call<List<QuoteData>>
}