package com.example.vinilosgrupo3.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class DetailRepository (val application: Application) {
    fun refreshData(albumId:Int, callback: (Album)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getDetail(albumId,{
            callback(it)
        },{
            onError
        })
    }

}