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

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(
            position: Int,
            data: List<String>
        ) {
            itemView.tvNsxReport.text = data[1]
            itemView.tvHsdReport.text = data[2]
            itemView.tvSttReport.text = data[0]
        }

    }

    val reportData = mutableListOf<List<String>>()
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

    fun setReportData(add: List<List<String>>) {
        this.reportData.clear()
        this.reportData.addAll(add)
        notifyDataSetChanged()
    }
}