package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.dang_nhap.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dang_nhap)
        setupViews();
    }

    private fun setupViews() {
        btnDn.setOnClickListener {
            val username = edtUsername.text.toString()
            val pwd = edtPwd.text.toString()
            if (username == "nhom3" && pwd == "1234"){
                startActivity(Intent(this, ControlActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}