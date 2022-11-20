package com.example.vinilosgrupo3.repositories
import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Musician
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class MusicianRepository (val application: Application) {
    fun refreshData(callback: (List<Musician>)->Unit, onError: (VolleyError)->Unit){
        NetworkServiceAdapter.getInstance(application).getMusicians({
            callback(it)
        },{
            onError
        })
    }
}