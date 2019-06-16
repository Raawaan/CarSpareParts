package com.example.carspareparts

import android.os.Parcelable
import com.parse.ParseObject
import kotlinx.android.parcel.Parcelize

/**
 * Created by rawan on 02/06/19 .
 */
@Parcelize
data class SparePartDetails(
    val objectId: String?,
    val name: String?,
    val type: String?,
    val price: Int?,
    val supplierId: ParseObject?,
    val sparePartId: ParseObject?,
    val description: String?,
    val supplierName: String?,
    val image: String?,
    val supplierLogo: String?,
    val delivered:Boolean,
    val quantity:Int
) : Parcelable