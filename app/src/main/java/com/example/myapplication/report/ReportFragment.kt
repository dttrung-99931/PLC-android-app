package com.example.myapplication.report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_report.*

/**
 * Created by Trung on 1/25/2021
 */
class ReportFragment: Fragment() {
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
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupReportRecyclerView()
        mRefreshLayout.setOnRefreshListener {
            refresh()
            mRefreshLayout.isRefreshing = false
            if (mBtnLoadReport.visibility == View.VISIBLE) {
                mBtnLoadReport.visibility = View.GONE
            }
        }
        mBtnLoadReport.setOnClickListener {
            refresh()
            mBtnLoadReport.visibility = View.GONE
        }
    }

    val reportAdapter = ReportAdapter()
    private fun setupReportRecyclerView() {
        mRecyclerView.adapter =  reportAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        refresh()
    }

    private fun refresh() {
        reportAdapter.setReportData(mainActivity.reportData)
    }

}