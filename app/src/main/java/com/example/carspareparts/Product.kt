package com.example.carspareparts

import com.parse.ParseClassName
import com.parse.ParseObject
@ParseClassName("supplier_spare_part")
data class SupplierSparePart(
    val price: Double?):ParseObject()