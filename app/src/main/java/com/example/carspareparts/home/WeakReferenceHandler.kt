package com.example.carspareparts.home

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

abstract class WeakReferenceHandler<T>(reference: T) : Handler() {
    private val mReference: WeakReference<T> = WeakReference(reference)

    override fun handleMessage(msg: Message) {
        if (mReference.get() == null)
            return
        handleMessage(mReference.get(), msg)
    }

    protected abstract fun handleMessage(reference: T?, msg: Message)
}