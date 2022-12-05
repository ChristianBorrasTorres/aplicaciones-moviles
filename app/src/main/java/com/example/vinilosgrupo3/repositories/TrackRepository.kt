package com.example.vinilosgrupo3.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Track
import com.example.vinilosgrupo3.network.CacheManager
import com.example.vinilosgrupo3.network.NetworkServiceAdapter
import org.json.JSONObject

class TrackRepository (val application: Application) {

    /*suspend fun refreshData(): List<Track> {
        var potentialResp = CacheManager.getInstance(application.applicationContext).getAlbums()
        if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            var tracks = NetworkServiceAdapter.getInstance(application).getTracks()
            //CacheManager.getInstance(application.applicationContext).addAlbums(tracks)
            return tracks
        }
        else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp
        }
    }*/

    suspend fun addTrack(albumId: Int,track: JSONObject):Track{
        Log.d("Args","Agregar Track")
        //CacheManager.getInstance(application.applicationContext).deleteTracks()
        return NetworkServiceAdapter.getInstance(application).addTrackToAlbum(albumId,track)
    }
}