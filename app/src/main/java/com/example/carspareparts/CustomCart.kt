package com.example.carspareparts

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.ImageView
import androidx.core.view.ViewCompat.getClipBounds
import android.R.attr.bottom
import android.R.attr.right
import android.R.attr.top
import android.R.attr.left
import com.parse.ParseObject
import com.parse.ParseQuery


/**
 * Created by rawan on 06/06/19 .
 */
class CustomCart(context: Context, attrs: AttributeSet?) :ImageView(context, attrs){
    private var paint = Paint()
    private val textPaint= TextPaint(Paint.ANTI_ALIAS_FLAG)
    private var value =0

    init {
        background=context.getDrawable(R.drawable.ic_shopping_cart_black_24dp)
        paint.color=context.resources.getColor(R.color.colorAccent)
    }


    override fun onDraw(canvas: Canvas?) {
        val centerX =width*0.7f
        val centerY=height*0.2f
        val radius = if(width>height) height*0.2f else width*0.2f

        val textOffSetX= textPaint.measureText(value.toString())*0.5f
        val textOffSetY=textPaint.fontMetrics.ascent*(0.5f)

        textPaint.color= Color.WHITE
        textPaint.textSize=10f*resources.displayMetrics.scaledDensity
        canvas?.drawCircle(centerX,centerY,radius,paint)

        paint.typeface=Typeface.create("Arial",Typeface.BOLD)
        canvas?.drawText(value.toString(),width*0.63f,height*0.3f,textPaint)
//
//        val d = resources.getDrawable(R.drawable.ic_shopping_cart_black_24dp, null)
//        d.setBounds(left, top, right, bottom)
//        d.draw(canvas!!)


    }
    fun getAllInCart(){
        val cartQuery= ParseQuery.getQuery<ParseObject>("pinned_order")
        cartQuery.fromLocalDatastore()
        cartQuery.findInBackground { objects, e ->
            if(e==null){
                value = objects.size
                invalidate()
            }

        }
    }
}