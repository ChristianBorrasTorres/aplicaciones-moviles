package com.example.vinilosgrupo3.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.models.Collector
import com.example.vinilosgrupo3.models.Musician
import com.example.vinilosgrupo3.network.CacheManager
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class MusicianRepository (val application: Application) {
    suspend fun refreshData(): List<Musician> {
        var potentialResp = CacheManager.getInstance(application.applicationContext).getMusicians()
        if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            var musicians = NetworkServiceAdapter.getInstance(application).getMusicians()
            CacheManager.getInstance(application.applicationContext).addMusicians(musicians)
            return musicians
        }
        else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp
        }
    }
}