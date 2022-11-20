package com.example.vinilosgrupo3.repositories
import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Collector
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class CollectorRepository (val application: Application) {
    fun refreshData(callback: (List<Collector>)->Unit, onError: (VolleyError)->Unit){
        NetworkServiceAdapter.getInstance(application).getCollectors({
            callback(it)
        },{
            onError
        })
    }
}