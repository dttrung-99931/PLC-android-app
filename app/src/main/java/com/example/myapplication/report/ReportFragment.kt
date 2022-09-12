package com.example.myapplication.report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_report.*

/**
 * Created by Trung on 1/25/2021
 *
 * Màn hình báo cáo danh sách các tsc1, tsc2 theo thời gian
 */
class ReportFragment: Fragment() {
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    /** Cài giaoo diện điều khiển*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    /** Cài đặt các giao diện thành phần*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /** Cài đặt danh sách report*/
        setupReportRecyclerView()

        /** Reload dữ liệu khi thao tác kéo tay từ trên xuống (pull)*/
        mRefreshLayout.setOnRefreshListener {
            refresh()
            mRefreshLayout.isRefreshing = false
            if (mBtnLoadReport.visibility == View.VISIBLE) {
                mBtnLoadReport.visibility = View.GONE
            }
        }

        /** Cài load dữ khi ấn button 'load report' */
        mBtnLoadReport.setOnClickListener {
            refresh()
            mBtnLoadReport.visibility = View.GONE
        }
    }

    /** Cài đặt adapter để load liệu hiển thị vào giao diện*/
    val reportAdapter = ReportAdapter()
    private fun setupReportRecyclerView() {
        mRecyclerView.adapter =  reportAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        refresh()
    }

    /** Refresh lại list report*/
    private fun refresh() {
        reportAdapter.setReportData(mainActivity.reportData.toList())
        mRecyclerView.scrollToPosition(reportAdapter.itemCount - 1)
    }

}