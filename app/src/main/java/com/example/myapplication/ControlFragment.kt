package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.api.api
import kotlinx.android.synthetic.main.dieu_khien.*
import retrofit2.Call

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
        return inflater.inflate(R.layout.dieu_khien, container, false)
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
        /** Cài đặt Cập nhật liên tục nsx, hsd, ... so sp loi*/
        updateRepleatly()

        /** Cài đặt view đặt sl sp lỗi giới hạn*/
        setupControlView(
            btnUpdateFailureProductLimit,
            edtFailureProductionLimit,
            api::setSoSphamLoiNgungHthong,
            tvSluongSphamNgungHThong,
            api::getSLSphamLoiNgungHeThong
        )

        /** Cài đặt view bật hệ thống*/
        setupControlView(
            btnTurnOn,
            edtOn,
            api::setSystemOn,
            tvSystemOn,
            api::getSystemOn
        )

        /** Cài đặt view đặt sl sp lỗi giới hạn*/
        setupControlView(
            btnTurnOff,
            edtOff,
            api::setSystemOff,
            tvSystemOff,
            api::getSystemOff
        )
    }

    var nsx: String = ""
    var hsd: String = ""

    /** Update nsx, hsd, số sản phẩm, số lượng sp lỗi ngưng hthong, on, off  */    
    private fun updateRepleatly() {
        /** Goi API giá trị analog */
        api.getNsx().enqueue(object : StringCallback<String?>() {
            /** Hàm này được gọi để nhận giá trị nsx, khi gọi thành công API */
            override fun onSuccessResponse(response: String) {
                /** Cập nhật giá trị analog nhận được từ API*/
                tvNsx.text = response
                nsx = response
            }
            override fun onFailure(call: Call<String?>, t: Throwable) {
                tvNsx.text = ""
                nsx = ""
                super.onFailure(call, t)
            }
        })

        /** Tương tự như API lấy giá trị nsx bên trên*/
        api.getHsd().enqueue(object : StringCallback<String?>() {
            override fun onSuccessResponse(response: String) {
                tvHsd.text = response
                hsd = response
                collectNsxHsdToReport()
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                tvHsd.text = ""
                hsd = ""
                super.onFailure(call, t)
            }
        })

        /** Tương tự như API lấy giá trị analog bên trên*/
        api.getSoSpLoi().enqueue(object : StringCallback<String?>() {
            override fun onSuccessResponse(response: String) {
                tvSoSpLoi.text = response
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                tvSoSpLoi.text = "0"
                super.onFailure(call, t)
            }
        })

        /** Lập lịch gọi lại hàm cập nhật analog, digital [updateRepleatly] sau mỗi 1 giấy*/
        handler.postDelayed(
            {updateRepleatly()},
            1000
        )
    }

    private fun collectNsxHsdToReport() {
        if (nsx.isNotEmpty() && hsd.isNotEmpty()){
            mainActivity.validNsxHsd.postValue(listOf(nsx, hsd))
            nsx = ""
            hsd = ""
        }
    }

    private fun setupControlView(
        controlBtn: Button, intEdt: EditText, setAPI: (Int) ->  Call<String>, valueTv: TextView, getAPI: () ->  Call<String>) {

        controlBtn.setOnClickListener {
            /** Lây giá trị tsc1(tần số cấp 1) đã được nhập*/
            val value: Int = intEdt.text.toString().toInt()

            /** Gọi API set sl sp ngung he thong*/
            setAPI(value).enqueue(
                object : StringCallback<String?>() {
                    override fun onSuccessResponse(response: String) {
                        /** Gọi hàm update tần số cấp 1 khi gọi API set tsc1 thành công*/
                        updateValueView(valueTv, getAPI)
                    }
                }
            )
        }

        updateValueView(valueTv, getAPI)
    }

    private fun updateValueView(valueTv: TextView, apiToGetValue: () ->  Call<String>) {
        apiToGetValue().enqueue(object : StringCallback<String?>() {
            override fun onSuccessResponse(response: String) {
                valueTv.text = response
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                valueTv.text = ""
                super.onFailure(call, t)
            }
        })
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

}