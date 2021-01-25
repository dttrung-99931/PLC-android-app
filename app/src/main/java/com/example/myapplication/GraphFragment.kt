import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.MainActivity
import com.example.myapplication.StringCallback
import com.example.myapplication.api.api
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_graph.*
import java.text.SimpleDateFormat
import java.util.*

/** Màn hình biểu đồ*/
class GraphFragment : Fragment(), OnChartValueSelectedListener {
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        repeatUpdateTs()
    }

    /** Cài đặt chọn giao diện [fragment_graph] để điều khiển */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_graph,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupChart()
    }

    private var times = mutableListOf<Long>()
    private val handler = Handler()

    /** Cài đặ biểu đồ: các đường biểu đồ, trục x, y, màu sắc */
    private fun setupChart() {

        chart.setOnChartValueSelectedListener(this)
        chart.setDrawGridBackground(false)
        chart.description.isEnabled = false
        chart.setNoDataText("No chart data available. Use the menu to add entries and data sets!")
        chart.data = LineData()

        setupAxises()
        addDataSet("Tần số", Color.BLACK)

        /** Bắt đầu thêm dữ liệu vào biểu đồ liên tục*/
        handler.postDelayed(::repeatAddGraphData, 1000)
    }

    /** Cài đặt các trục tọa đồ cho biểu đò*/
    private fun setupAxises() {
        chart.axisRight.mAxisMaximum = 100f
        chart.axisLeft.mAxisMaximum = 100f

        chart.xAxis.valueFormatter = object : ValueFormatter() {
            private val mFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)

            override fun getFormattedValue(value: Float): String {
                return mFormat.format(times[value.toInt()])
            }
        }
    }

    /** Liên tục load thêm dữ liệu cho biểu đồ sau mỗi s1 */
    private fun repeatAddGraphData() {
        if (ts != null) {
            times.add(System.currentTimeMillis())

            val data = chart.data

            ts?.let {
                data.addEntry(
                    Entry((times.size - 1).toFloat(), it), 0
                )
            }
            data.notifyDataChanged()

            chart.notifyDataSetChanged()
            chart.setVisibleXRangeMaximum(6f)

            chart.moveViewTo(data.entryCount - 7.toFloat(), 50f, AxisDependency.LEFT)
        }

        handler.postDelayed(::repeatAddGraphData, 1000)
    }

    private var ts: Float? = null

    /** Lắng nghe để cập nhật các giá trị tsc1, tsc2*/
    private fun repeatUpdateTs() {
        api.getTs().enqueue(object : StringCallback<String?>() {
            override fun onSuccessResponse(response: String) {
                ts = response.toFloat()
                mainActivity.tsLD.postValue(ts)
            }
        })
        handler.postDelayed(::repeatUpdateTs, 1000)
    }

    private fun addDataSet(dataSetName: String, color: Int) {
        val data = chart.data
        val set = LineDataSet(mutableListOf(), dataSetName)
        set.lineWidth = 2.5f
        set.circleRadius = 4.5f
        set.color = color
        set.setCircleColor(color)
        set.highLightColor = color
        set.valueTextSize = 10f
        set.valueTextColor = color
        data.addDataSet(set)
        data.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    override fun onValueSelected(
        e: Entry,
        h: Highlight?
    ) {
        Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected() {}
}