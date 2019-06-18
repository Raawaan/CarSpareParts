package com.example.carspareparts

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by rawan on 31/05/19 .
 */

fun String.isValidEmailAddress(): Boolean {
    val ePattern ="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"
    val p = java.util.regex.Pattern.compile(ePattern)
    val m = p.matcher(this)
    return m.matches()
}

fun ImageView.setImageUrl(url: String?) = Picasso.get().load(url).into(this)