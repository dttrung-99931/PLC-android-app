package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.content_dieu_khien.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ControlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dieu_khien)
        init()
        setupViews()
    }

    private lateinit var handler: Handler
    private lateinit var apiService: ApiService
    private fun init() {
        apiService = Retrofit.Builder()
            .baseUrl("http://1.52.251.245/awp/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        handler = Handler()
    }

    private fun setupViews() {
        updateAnalogAndDigital()
        setupViewTsoC1()
        setupViewTsoC2()
        setupViewBatDc()
        setupViewTatDc()
    }

    private fun setupViewTatDc() {
        swTdc.setOnCheckedChangeListener { buttonView, isChecked ->
            val bat: Int = if (isChecked) 1 else 0
            apiService.setTatDc(bat).enqueue(
                object : StringCallback<String?>() {
                    override fun onSuccessResponse(response: String) {
                        updateTatDc()
                    }
                }
            )
        }
        updateTatDc()
    }

    private fun updateTatDc() {
        apiService.getTatDc().enqueue(object : StringCallback<String?>() {
            override fun onSuccessResponse(response: String) {
                val tat = response == "1"
                cbTdc.isChecked = tat
                handler.postDelayed(
                    {updateTatDc() },
                    1000
                )
            }
        })
    }
    private fun setupViewBatDc() {
        swDcc.setOnCheckedChangeListener { buttonView, isChecked ->
            val bat: Int = if (isChecked) 1 else 0
            apiService.setBatDc(bat).enqueue(
                object : StringCallback<String?>() {
                    override fun onSuccessResponse(response: String) {
                        updateBatDc()
                    }
                }
            )
        }
        updateBatDc()
    }

    private fun updateBatDc() {
        apiService.getBatDc().enqueue(object : StringCallback<String?>() {
            override fun onSuccessResponse(response: String) {
                val bat = response == "1"
                cbDcc.isChecked = bat
                handler.postDelayed(
                    {updateBatDc() },
                    1000
                )
            }
        })
    }

    private fun updateAnalogAndDigital() {
        apiService.getAnalog().enqueue(object : StringCallback<String?>() {
            override fun onSuccessResponse(response: String) {
                tvAnalog.text = response
            }
        })

        apiService.getDigital().enqueue(object : StringCallback<String?>() {
            override fun onSuccessResponse(response: String) {
                tvDigital.text = response
            }
        })

        handler.postDelayed(
            {updateAnalogAndDigital()},
            1000
        )
    }

    private fun setupViewTsoC1() {
        btnOkTssc1.setOnClickListener {
            val tssc1: Int = edtTssc1.text.toString().toInt()
            apiService.setTssc1(tssc1).enqueue(
                object : StringCallback<String?>() {
                    override fun onSuccessResponse(response: String) {
                        updateTsoC1()
                    }
                }
            )
        }

        updateTsoC1()
    }

    private fun updateTsoC1() {
        apiService.getTssc1().enqueue(object : StringCallback<String?>() {
            override fun onSuccessResponse(response: String) {
                tvTssc1.text = response
                handler.postDelayed(
                    {updateTsoC1() },
                    1000
                )
            }
        })
    }

    private fun setupViewTsoC2() {
        btnOkTsc2.setOnClickListener {
            val tsc2: Int = edtTsc2.text.toString().toInt()
            apiService.setTssc2(tsc2).enqueue(
                object : StringCallback<String?>() {
                    override fun onSuccessResponse(response: String) {
                        updateTsoC2()
                    }
                }
            )
        }
        updateTsoC2()
    }

    private fun updateTsoC2() {
        apiService.getTsc2().enqueue(object : StringCallback<String?>() {
            override fun onSuccessResponse(response: String) {
                tvTsc2.text = response
                handler.postDelayed(
                    {updateTsoC2() },
                    1000
                )
            }
        })
    }
}