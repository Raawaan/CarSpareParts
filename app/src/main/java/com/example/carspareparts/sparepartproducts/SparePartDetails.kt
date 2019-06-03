package com.example.carspareparts.sparepartproducts

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by rawan on 02/06/19 .
 */
@Parcelize
data class SparePartDetails(val name:String?,
                            val type:String?,
                            val price:Int?,
                            val description:String?,
                            val supplierName:String?,
                            val image:String?): Parcelable