package com.example.vinilosgrupo3.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Collector
import com.example.vinilosgrupo3.models.Musician
import com.example.vinilosgrupo3.network.CacheManager
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class MusicianDetailRepository (val application: Application) {
    suspend fun refreshData(musicianId:Int) : Musician {
        var potentialResp = CacheManager.getInstance(application.applicationContext).getMusician(musicianId)
        if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            var musician = NetworkServiceAdapter.getInstance(application).getMusicianDetail(musicianId)
            CacheManager.getInstance(application.applicationContext).addMusician(musician.musicianId, musician)
            return musician
        }
        else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp[0]
        }
    }
}