package com.example.carspareparts.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.parse.ParseObject
import com.parse.ParseQuery

class HomeFragmentViewModel : ViewModel() {
    private val sparePartTypeList = MutableLiveData<List<ParseObject>>()
    fun getSparePartTypeList(){
        val sparePartTypeQuery = ParseQuery<ParseObject>("spare_part_type")
        sparePartTypeQuery.findInBackground { objects, e ->
            if (e==null){
                sparePartTypeList.postValue(objects)
            }
//            else
//                sparePartTypeList.postValue(e)
        }
    }
    fun getSparePartTypeLiveData(): MutableLiveData<List<ParseObject>> {
        return sparePartTypeList
    }
}
//
//class HomeFragmentViewModel : ViewModel() {
//    private val sparePartTypeList = MutableLiveData<List<ParseObject>>()
//    private val set= mutableSetOf<String?>()
//    fun getSparePartTypeList(){
//        val sparePartTypeQuery = ParseQuery<ParseObject>("spare_part")
//        sparePartTypeQuery.include("spare_part_type_id")
//        sparePartTypeQuery.findInBackground { objects, e ->
//            if (e==null){
//                objects.forEach {
//                    set.add(it.getString("spare_part_type_id"))
//                }
//                sparePartTypeList.postValue(objects)
//            }
////            else
////                sparePartTypeList.postValue(e)
//        }
//    }
//    fun getSparePartTypeLiveData(): MutableLiveData<List<ParseObject>> {
//        return sparePartTypeList
//    }
//}
