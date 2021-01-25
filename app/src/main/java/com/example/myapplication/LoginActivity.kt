package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.dang_nhap.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dang_nhap)

        /** Cài đặt xử lý khi btnDN (phím đăng nhập) được ấn*/
        setOnBtnLoginClicked()
    }

    private fun setOnBtnLoginClicked() {
        /**
         * Đăng ký nhận sự kiện khi phím đăng nhập được ấn
         * khi ấn phìm đăng nhập, khối code trong dấu {} sẽ đươc thực thi
         * */
        btnDn.setOnClickListener {
            /** Lấy username và password đã nhập*/
            val username = edtUsername.text.toString()
            val pwd = edtPwd.text.toString()

            /** Kiếm tra đăng nhập*/
            if (username == "nhom3" && pwd == "1234"){ // Nếu đúng username && password
                /** Chuyển qua màn hình điểu khiển*/
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else { /** Nếu sai username or password*/
                /** Thông báo "Login failed"*/
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}