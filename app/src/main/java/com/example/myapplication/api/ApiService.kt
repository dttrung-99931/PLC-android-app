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
    @POST("rs_value_c1.html")
    // TODO: FIx param name
    fun setSoSphamLoiNgungHthong(@Field("\"button\".SL_error") tssc1: Int): Call<String>

    @FormUrlEncoded
    @POST("ON.html")
    // TODO: FIx param name
    fun setSystemOn(@Field("\"button\".OM") value: Int): Call<String>


    @FormUrlEncoded
    @POST("OFF.html")
    // TODO: FIx param name
    fun setSystemOff(@Field("\"button\".OFF") value: Int): Call<String>

    @FormUrlEncoded
    @POST("rs_value_c2.html")
    fun setTssc2(@Field("\"value_tan_so\".tan_so_dat_cap_2") tssc2: Int): Call<String>

    @FormUrlEncoded
    @POST("bat.html")
    fun setBatDc(@Field("\"control\".start") bat: Int): Call<String>

    @GET("bat.html")
    fun getBatDc(): Call<String>

    @FormUrlEncoded
    @POST("tat.html")
    fun setTatDc(@Field("\"control\".stop") bat: Int): Call<String>

    @GET("tat.html")
    fun getTatDc(): Call<String>

    @GET("rs_value_c1.html")
    fun getTssc1(): Call<String>

    @GET("rs_value_c2.html")
    fun getTsc2(): Call<String>

    @GET("Tam.html")
    fun getTs(): Call<String>

    @GET("NSX.html")
    fun getNsx(): Call<String>

    @GET("HSD.html")
    fun getHsd(): Call<String>

    @GET("SL_Error.html")
    fun getSoSpLoi(): Call<String>

    // TODO: Fix
    @GET("SL_Error.html")
    fun getSLSphamLoiNgungHeThong(): Call<String>

    @GET("ON.html")
    fun getSystemOn(): Call<String>

    @GET("OFF.html")
    fun getSystemOff(): Call<String>
}