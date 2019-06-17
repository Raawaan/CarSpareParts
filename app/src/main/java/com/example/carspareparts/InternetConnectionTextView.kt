package com.example.carspareparts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.example.carspareparts.R

/**
 * Created by rawan on 29/05/19 .
 */
class InternetConnectionTextView(context: Context, attrs: AttributeSet?) : TextView(context, attrs) {
    constructor(context: Context) : this(context, null)

    init {
        setTextColor(Color.BLACK)
        textAlignment = View.TEXT_ALIGNMENT_CENTER
    }

    fun noInternetConnection() {
        text = context.getString(R.string.not_connected)
        visibility = View.VISIBLE
        background = resources.getDrawable(R.color.offline_red)
        invalidate()
    }

    fun connected() {
        text = context.getString(R.string.connected)
        background = resources.getDrawable(R.color.online_green)
        postDelayed({
                visibility = View.GONE
        }, 2000)
        invalidate()

    }


}