package com.example.vinilosgrupo3.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.models.Collector
import com.example.vinilosgrupo3.network.CacheManager
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class CollectorDetailRepository (val application: Application) {
    suspend fun refreshData(collectorId:Int): Collector {
        var potentialResp = CacheManager.getInstance(application.applicationContext).getCollector(collectorId)
        if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            var collector = NetworkServiceAdapter.getInstance(application).getCollectorsDetail(collectorId)
            CacheManager.getInstance(application.applicationContext).addCollector(collector.id, collector)
            return collector
        }
        else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp[0]
        }
    }
}