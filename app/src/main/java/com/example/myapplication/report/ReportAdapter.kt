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
 */
class ReportAdapter : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {
    val simpleDataFormatter = SimpleDateFormat("HH:mm:ss")

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(
            position: Int,
            data: Pair<Long, Pair<Float?, Float?>>
        ) {
            itemView.mTvTime.text = simpleDataFormatter.format(
                data.first * 1000
            )
            if (data.second.first != null) {
                val tsc1 = "TSC1 ${data.second.first}"
                itemView.mTvTsc1.text = tsc1
            }
            if (data.second.second != null) {
                val tsc2 = "TSC2 ${data.second.second}"
                itemView.mTvTsc2.text = tsc2
            }
        }

    }

    val reportData = mutableListOf<Pair<Long, Pair<Float?, Float?>>>()
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

    fun setReportData(reportDataMap: MutableMap<Long, Pair<Float?, Float?>>) {
        reportData.clear()
        reportData.addAll(
            reportDataMap.map { item ->
                Pair(item.key, item.value)
            }.toList()
        )
        notifyDataSetChanged()
    }
}