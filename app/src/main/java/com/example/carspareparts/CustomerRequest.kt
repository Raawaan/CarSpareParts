package com.example.carspareparts
import com.example.carspareparts.Product
import com.example.carspareparts.User


    data class CustomerRequest(
        val user: User,
        val price: Double?,
        val products: List<Product>
    )
