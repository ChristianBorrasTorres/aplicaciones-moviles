package com.example.vinilosgrupo3.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Musician
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class MusicianDetailRepository (val application: Application) {
    fun refreshData(musicianId:Int, callback: (Musician)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getMusicianDetail(musicianId,{
            callback(it)
        },{
            onError
        })
    }

}