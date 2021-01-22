package com.example.myapplication
import android.util.Log
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response

/**
 * Created by Trung on 1/22/2021
 */
abstract class StringCallback<T>: Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.d("DDD", "onFailure: ${call.request().url()} ${t.message}")
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        onSuccessResponse(response.body().toString())
    }

    abstract fun onSuccessResponse(response: String);
}