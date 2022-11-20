package com.example.vinilosgrupo3.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class AlbumRepository (val application: Application) {
    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit){
        NetworkServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        },{
            onError
        })
    }
}