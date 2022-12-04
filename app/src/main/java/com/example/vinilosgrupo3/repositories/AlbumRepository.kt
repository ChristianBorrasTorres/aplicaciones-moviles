package com.example.vinilosgrupo3.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.network.CacheManager
import com.example.vinilosgrupo3.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository (val application: Application) {

    suspend fun refreshData(): List<Album> {
        var potentialResp = CacheManager.getInstance(application.applicationContext).getAlbums()
        if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            var albums = NetworkServiceAdapter.getInstance(application).getAlbums()
            CacheManager.getInstance(application.applicationContext).addAlbums(albums)
            return albums
        }
        else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp
        }
    }

    suspend fun createAlbum(album: JSONObject):Album{
        Log.d("Args","Crear Album")
        return NetworkServiceAdapter.getInstance(application).createAlbum(album)
    }
}