package com.example.vinilosgrupo3.repositories

import android.app.Application
import android.util.Log
import com.example.vinilosgrupo3.models.Track
import com.example.vinilosgrupo3.network.NetworkServiceAdapter
import org.json.JSONObject

class TrackRepository (val application: Application) {

    suspend fun addTrack(albumId: Int,track: JSONObject):Track{
        Log.d("Args","Agregar Track")
        return NetworkServiceAdapter.getInstance(application).addTrackToAlbum(albumId,track)
    }
}