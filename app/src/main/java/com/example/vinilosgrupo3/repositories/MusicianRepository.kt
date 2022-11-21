package com.example.vinilosgrupo3.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.models.Musician
import com.example.vinilosgrupo3.network.NetworkServiceAdapter

class MusicianRepository (val application: Application) {
    suspend fun refreshData(): List<Musician> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getMusicians()
    }
}