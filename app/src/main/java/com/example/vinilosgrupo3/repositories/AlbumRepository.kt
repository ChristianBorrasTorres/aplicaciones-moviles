package com.example.vinilosgrupo3.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.database.AlbumsDao
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class AlbumRepository (val application: Application,private val albumsDao: AlbumsDao) {
    /*fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit){
        NetworkServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        },{
            onError
        })

    }*/
    suspend fun refreshData(): List<Album> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        var cached = albumsDao.getAlbums()
        return if(cached.isNullOrEmpty()){
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else NetworkServiceAdapter.getInstance(application).getAlbums()
        } else cached
        //return NetworkServiceAdapter.getInstance(application).getAlbums()
    }
}