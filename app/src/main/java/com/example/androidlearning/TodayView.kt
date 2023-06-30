package com.example.androidlearning

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.text.SimpleDateFormat
import java.util.Calendar


class TodayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    init { setDate() }

    private fun setDate() {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val today = sdf.format(Calendar.getInstance().time)
        this.text = today
    }

    fun customize(viewData: TodayViewData) {
        this.textSize = viewData.textSize.toFloat()
        this.setTextColor(viewData.textColor)
        this.setBackgroundColor(viewData.backgroundColor)
    }
}

data class TodayViewData(
    val textSize: Int = 18,
    val textColor: Int = Color.BLACK,
    val backgroundColor: Int = Color.YELLOW
)