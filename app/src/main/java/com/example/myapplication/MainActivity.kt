package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Trung on 1/24/2021
 *
 * Là màn hình chính, xuất hiện sau khi login thành công
 * Màn hình này chữa [ControlFragment], [GraphFragment] và [ReportFragment]
 */
class MainActivity: AppCompatActivity() {
    /** 2 biến lưu ts truyền từ [GraphFragment] lên*/
    val tsLD = MutableLiveData<Float>()

    val validNsxHsd = MutableLiveData<List<String>>()

    /** Lưu dữ liệu report, được sử dụng ở [ReportFragment]*/
    val reportData = mutableListOf<List<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        observeToGatherReportData()
    }

    /** Lưu lại các [tsLD] lấy từ [GraphFragment] truyền lên*/
    private fun observeToGatherReportData() {
        validNsxHsd.observe(this, Observer {
            reportData.add(0, it)
        })
    }


    /** Cài đặt các thành phân giao diện*/
    private fun setupViews() {
        setupViewPager2()
        setupBottomNavMenu()
    }

    /** Cài đặt container page, là view sẽ chứa  [ControlFragment], [GraphFragment] và [ReportFragment]*/
    private fun setupViewPager2() {
        mViewPager2.adapter = MainFragmentAdapter(supportFragmentManager, this.lifecycle)
        mViewPager2.offscreenPageLimit = 2
        mViewPager2.isUserInputEnabled = false
    }

    var showRefreshInstructionCount = 0
    /** Cài đặt menu ở dưới đáy*/
    private fun setupBottomNavMenu() {
        mBottomNavMenu.setOnNavigationItemSelectedListener {
            val currentItemIndex = when (it.itemId) {
                R.id.item_menu_home -> 0
                else -> 1
            }
            mViewPager2.setCurrentItem(currentItemIndex, true)
            if (currentItemIndex == 1 && showRefreshInstructionCount < 2){
                Toast.makeText(this, "Kéo xuống để reload report", Toast.LENGTH_LONG).show()
                showRefreshInstructionCount++
            }
            true
        }
    }

}