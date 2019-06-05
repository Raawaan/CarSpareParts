package com.example.carspareparts.main

import androidx.fragment.app.Fragment

/**
 * Created by rawan on 05/06/19 .
 */
interface ReplaceFragment{
    fun replaceFragmentListener(fragment: Fragment,pair: Pair<String,String?>)
}