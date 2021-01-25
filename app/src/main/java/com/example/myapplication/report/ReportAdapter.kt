package com.example.myapplication.report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.item_report.view.*
import java.text.SimpleDateFormat


/**
 * Created by Trung on 1/25/2021
 *
 * Dùng để để load dữ liệu report hiển thị lên giao diện
 */
class ReportAdapter : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {
    val simpleDataFormatter = SimpleDateFormat("HH:mm:ss")

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(
            position: Int,
            data: Pair<Long, Float>
        ) {
            itemView.mTvTime.text = simpleDataFormatter.format(
                data.first * 1000
            )
            val ts = "${data.second} hz"
            itemView.mTvTs.text = ts
        }

    }

    val reportData = mutableListOf<Pair<Long, Float>>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reportData.size
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bindData(position, reportData[position])
    }

    fun setReportData(reportData: List<Pair<Long,Float>>) {
        this.reportData.clear()
        this.reportData.addAll(reportData)
        notifyDataSetChanged()
    }
}