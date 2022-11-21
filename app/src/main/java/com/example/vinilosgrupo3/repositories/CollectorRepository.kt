package com.example.vinilosgrupo3.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.models.Collector
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class CollectorRepository (val application: Application) {
    suspend fun refreshData(): List<Collector>{
        return NetworkServiceAdapter.getInstance(application).getCollectors()

    }
}