package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.api.api
import kotlinx.android.synthetic.main.content_dieu_khien.*

/**
 * Created by Trung on 1/25/2021
 */
class ControlFragment: Fragment() {
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dieu_khien, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        setupViews()
    }

    /** Dùng để lập lịch gọi lại các API sau mỗi 1 giây*/
    private lateinit var handler: Handler

    /** Khởi tạo [api] và [handler] khi mở màn hình điều khiển (tức là sau khi đăng nhập thành công) */
    private fun init() {
        handler = Handler()
    }

    /** Setup cho các thành phần điều khiển*/
    private fun setupViews() {
        /** Cài đặt Cập nhật liên tục analog và digital*/
        updateAnalogAndDigital()

        /** Cài đặt xủ lý tần số cấp 1*/
        setupViewTsoC1()
        /** ....*/
        setupViewTsoC2()

        /** Cài đặt xử lý bật động cơ*/
        setupViewBatDc()

        /** Cài đặt xử lý tắt động cơ*/
        setupViewTatDc()
    }

    private fun updateAnalogAndDigital() {
        /** Goi API giá trị analog */
        api.getAnalog().enqueue(object : StringCallback<String?>() {
            /** Hàm này được gọi để nhận giá trị analog, khi gọi thành công API */
            override fun onSuccessResponse(analogValue: String) {
                /** Cập nhật giá trị analog nhận được từ API*/
                tvAnalog.text = analogValue
            }
        })

        /** Tương tự như API lấy giá trị analog bên trên*/
        api.getDigital().enqueue(object : StringCallback<String?>() {
            override fun onSuccessResponse(response: String) {
                tvDigital.text = response
            }
        })

        /** Lập lịch gọi lại hàm cập nhật analog, digital [updateAnalogAndDigital] sau mỗi 1 giấy*/
        handler.postDelayed(
            {updateAnalogAndDigital()},
            1000
        )
    }

    private fun setupViewTsoC1() {
        /** Đăng ký nhận sự kiện khi phím OK của tần số cấp 1 được ấn*/
        btnOkTssc1.setOnClickListener {
            /** Lây giá trị tsc1(tần số cấp 1) đã được nhập*/
            val tsc1: Int = edtTssc1.text.toString().toInt()

            if (tsc1 > 60) {
                showWarningDialog(R.string.warning_tan_so)
                return@setOnClickListener
            }
            /** Gọi API set tsc1*/
            api.setTssc1(tsc1).enqueue(
                object : StringCallback<String?>() {
                    override fun onSuccessResponse(response: String) {
                        /** Gọi hàm update tần số cấp 1 khi gọi API set tsc1 thành công*/
                        updateTsoC1()
                    }
                }
            )
        }

        updateTsoC1()
    }

    private lateinit var alertDialog: AlertDialog
    private fun showWarningDialog(msgRes: Int) {
        if (!::alertDialog.isInitialized) {
            alertDialog = AlertDialog.Builder(requireContext())
                .setNegativeButton("OK", null)
                .create()
        }
        alertDialog.setMessage(getString(msgRes))
        alertDialog.show()
    }

    private fun updateTsoC1() {
        /** Gọi API lấy tần số cấp 1*/
        api.getTssc1().enqueue(object : StringCallback<String?>() {
            /** Hàm được gọi khi API tsc1 trả về kết quả*/
            override fun onSuccessResponse(response: String) {
                /** Cập nhật tần số cấp 1 trả vể về từ API lên giao diện*/
                tvTssc1.text = response

                /** Lặp lịch gọi lại hàm [updateTsoC1] sau 1 giấy*/
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
            if (tsc2 > 60) {
                showWarningDialog(R.string.warning_tan_so)
                return@setOnClickListener
            }

            api.setTssc2(tsc2).enqueue(
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
        api.getTsc2().enqueue(object : StringCallback<String?>() {
            override fun onSuccessResponse(response: String) {
                tvTsc2.text = response
                handler.postDelayed(
                    {updateTsoC2() },
                    1000
                )
            }
        })
    }

    private fun setupViewBatDc() {
        swDcc.setOnCheckedChangeListener { buttonView, isChecked ->
            val bat: Int = if (isChecked) 1 else 0
            api.setBatDc(bat).enqueue(
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
        api.getBatDc().enqueue(object : StringCallback<String?>() {
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

    private fun setupViewTatDc() {
        swTdc.setOnCheckedChangeListener { buttonView, isChecked ->
            val bat: Int = if (isChecked) 1 else 0
            api.setTatDc(bat).enqueue(
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
        api.getTatDc().enqueue(object : StringCallback<String?>() {
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

}