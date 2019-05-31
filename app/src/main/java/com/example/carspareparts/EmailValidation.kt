package com.example.carspareparts

/**
 * Created by rawan on 31/05/19 .
 */

fun String.isValidEmailAddress(): Boolean {
    val ePattern ="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"
    val p = java.util.regex.Pattern.compile(ePattern)
    val m = p.matcher(this)
    return m.matches()
}