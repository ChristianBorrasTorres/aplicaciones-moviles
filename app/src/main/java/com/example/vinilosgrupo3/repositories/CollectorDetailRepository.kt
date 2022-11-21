package com.example.vinilosgrupo3.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Collector
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class CollectorDetailRepository (val application: Application) {
    suspend fun refreshData(collectorId: Int) : Collector  {
        return NetworkServiceAdapter.getInstance(application).getCollectorsDetail(collectorId)
    }
}