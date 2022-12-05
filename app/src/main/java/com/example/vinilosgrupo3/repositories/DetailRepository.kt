package com.example.vinilosgrupo3.repositories

import android.app.Application
import android.util.Log
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.network.CacheManager
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class DetailRepository (val application: Application) {
    suspend fun refreshData(albumId:Int): Album {
        var potentialResp = CacheManager.getInstance(application.applicationContext).getAlbum(albumId)
        if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            var album = NetworkServiceAdapter.getInstance(application).getDetail(albumId)
            CacheManager.getInstance(application.applicationContext).addAlbum(album.albumId, album)
            return album
        }
        else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp[0]
        }
    }
}