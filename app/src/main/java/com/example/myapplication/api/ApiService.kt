package com.example.myapplication.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * Created by Trung on 1/22/2021
 */
interface ApiService {

    @FormUrlEncoded
    @POST("Start.html")
    fun setSystemOn(@Field("\"button\".Start") value: Int): Call<String>


    @FormUrlEncoded
    @POST("Stop.html")
    fun setSystemOff(@Field("\"button\".Stop") value: Int): Call<String>

    @GET("Tam.html")
    fun getTs(): Call<String>

    @GET("NSX.html")
    fun getNsx(): Call<String>

    @GET("HSD.html")
    fun getHsd(): Call<String>

    @GET("SL_Error.html")
    fun getSoSpLoi(): Call<String>

    @GET("Set_Error.html")
    fun getSLSphamLoiNgungHeThong(): Call<String>

    @FormUrlEncoded
    @POST("Set_Error.html")
    fun setSoSphamLoiNgungHthong(@Field("\"SL_Error\".Set_Error") tssc1: Int): Call<String>

    @GET("Start.html")
    fun getSystemOn(): Call<String>

    @GET("Stop.html")
    fun getSystemOff(): Call<String>
}