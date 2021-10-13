package com.example.rp_week5

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val sRetrofit = initRetrofit()
    private const val MOVIE_URL = "https://api.themoviedb.org/3/"

    private fun initRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MOVIE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val cRetrofit = cgvRetrofit()

    private const val CGV_URL = "http://openapi.seoul.go.kr:8088/"

    private fun  cgvRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(CGV_URL)
            .client(OkHttpClient().newBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}