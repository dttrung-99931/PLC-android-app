package com.example.myapplication.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Trung on 1/25/2021
 */
val api: ApiService = Retrofit.Builder()
    .baseUrl("http://lvtnlocvi123.hopto.org/awp//")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(ApiService::class.java)
