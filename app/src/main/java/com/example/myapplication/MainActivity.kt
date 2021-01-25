package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Trung on 1/24/2021
 *
 * Là màn hình chính, xuất hiện sau khi login thành công
 * Màn hình này chữa [ControlFragment], [GraphFragment] và [ReportFragment]
 */
class MainActivity: AppCompatActivity() {
    /** 2 biến lưu tsc1 và tsc2 truyền từ [ControlFragment] lên*/
    val tsc1LD = MutableLiveData<Float>()
    val tsc2LD = MutableLiveData<Float>()

    /** Lưu dữ liệu report, được sử dụng ở [ReportFragment]*/
    val reportData = mutableMapOf<Long, Pair<Float?, Float?>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        observeToGatherReportData()
    }

    /** Cập nhật [tsc1LD] và [tsc2LD] lấy từ [ControlFragment] truyền lên*/
    private fun observeToGatherReportData() {
        tsc1LD.observe(this, Observer {
            val curSec = getCurrentSeconds()
            val tsc1Tsc2Pair = reportData[curSec]
            if (tsc1Tsc2Pair != null) {
                reportData[curSec] = Pair(it, tsc1Tsc2Pair.second)
            } else {
                reportData[curSec] = Pair(it, null)
            }
        })
        tsc1LD.observe(this, Observer {
            val curSec = getCurrentSeconds()
            val tsc1Tsc2Pair = reportData[curSec]
            if (tsc1Tsc2Pair != null) {
                reportData[curSec] = Pair(tsc1Tsc2Pair.first, it)
            } else {
                reportData[curSec] = Pair(null, it)
            }
        })
    }

    private fun getCurrentSeconds(): Long {
        return System.currentTimeMillis()/1000
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

    /** Cài đặt menu ở dưới đáy*/
    private fun setupBottomNavMenu() {
        mBottomNavMenu.setOnNavigationItemSelectedListener {
            val currentItemIndex = when (it.itemId) {
                R.id.item_menu_home -> 0
                R.id.item_menu_graph -> 1
                else -> 2
            }
            mViewPager2.setCurrentItem(currentItemIndex, true)
            true
        }
    }

}