package com.example.vinilosgrupo3.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Musician
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class MusicianDetailRepository (val application: Application) {
    suspend fun refreshData(musicianId:Int) : Musician {
        return NetworkServiceAdapter.getInstance(application).getMusicianDetail(musicianId)
    }
}