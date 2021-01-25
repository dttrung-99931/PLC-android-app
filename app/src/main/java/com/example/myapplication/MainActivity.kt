package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Trung on 1/24/2021
 */
class MainActivity: AppCompatActivity() {
    val tsc1LD = MutableLiveData<Float>()
    val tsc2LD = MutableLiveData<Float>()
    val reportData = mutableMapOf<Long, Pair<Float?, Float?>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        observeToGatherReportData()
    }

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

    private fun setupViews() {
        setupViewPager2()
        setupBottomNavMenu()
    }

    private val mOnPageChanged: ViewPager2.OnPageChangeCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                mBottomNavMenu.selectedItemId = if (position == 0)
                    R.id.item_menu_home else R.id.item_menu_graph
            }
        }
    private fun setupViewPager2() {
        mViewPager2.adapter = MainFragmentAdapter(supportFragmentManager, this.lifecycle)
        mViewPager2.offscreenPageLimit = 2
        mViewPager2.isUserInputEnabled = false
    }

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