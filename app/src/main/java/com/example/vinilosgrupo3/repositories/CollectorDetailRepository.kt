package com.example.vinilosgrupo3.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Collector
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class CollectorDetailRepository (val application: Application) {
    fun refreshData(collectorId:Int, callback: (Collector)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getCollectorsDetail(collectorId,{
            callback(it)
        },{
            onError
        })
    }
}