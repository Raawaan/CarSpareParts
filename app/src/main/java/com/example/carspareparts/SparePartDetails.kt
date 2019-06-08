package com.example.carspareparts

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by rawan on 02/06/19 .
 */
@Parcelize
data class SparePartDetails(val name:String?,
                            val type:String?,
                            val price:Int?,
                            val supplierId : String?,
                            val sparePartId:String?,
                            val description:String?,
                            val supplierName:String?,
                            val image:String?): Parcelable