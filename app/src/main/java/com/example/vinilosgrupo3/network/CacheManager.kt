package com.example.vinilosgrupo3.network
import android.content.Context
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.models.Collector
import com.example.vinilosgrupo3.models.Musician

class CacheManager(context: Context) {
    companion object {
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }


    // Cache Albums List
    private var albums = mutableListOf<Album>()

    fun addAlbums(newAlbums: List<Album>) {
        for (a in newAlbums) {
            albums.add(a)
        }
    }

    fun getAlbums(): List<Album> {
        return albums
    }

    fun deleteAlbums(): List<Album> {
        albums.clear()
        return albums
    }

    // Cache Album Detail
    private var albumsDetail: HashMap<Int, Album> = hashMapOf()
    fun addAlbum(albumId: Int, album: Album){
        if (!albumsDetail.containsKey(albumId)){
            albumsDetail[albumId] = album
        }
    }
    fun getAlbum(albumId: Int) : List<Album> {
        var shortlist = mutableListOf<Album>()
        if (albumsDetail.containsKey(albumId)) {
            albumsDetail[albumId]?.let { shortlist.add(it) }
        }
        return shortlist
    }


    // Cache Collectors List
    private var collectors = mutableListOf<Collector>()

    fun addCollectors(newCollectors: List<Collector>) {
        for (c in newCollectors) {
            collectors.add(c)
        }
    }

    fun getCollectors(): List<Collector> {
        return collectors
    }

    // Cache Collector Detail
    private var collectorsDetail: HashMap<Int, Collector> = hashMapOf()
    fun addCollector(collectorId: Int, collector: Collector){
        if (!collectorsDetail.containsKey(collectorId)){
            collectorsDetail[collectorId] = collector
        }
    }
    fun getCollector(collectorId: Int) : List<Collector> {
        var shortlist = mutableListOf<Collector>()
        if (collectorsDetail.containsKey(collectorId)) {
            collectorsDetail[collectorId]?.let { shortlist.add(it) }
        }
        return shortlist
    }

    // Cache Musicians
    private var musicians = mutableListOf<Musician>()

    fun addMusicians(newMusicians: List<Musician>) {
        for (m in newMusicians) {
            musicians.add(m)
        }
    }

    fun getMusicians(): List<Musician> {
        return musicians
    }

    // Cache Collector Detail
    private var musiciansDetail: HashMap<Int, Musician> = hashMapOf()
    fun addMusician(musicianId: Int, musician: Musician){
        if (!musiciansDetail.containsKey(musicianId)){
            musiciansDetail[musicianId] = musician
        }
    }
    fun getMusician(musicianId: Int) : List<Musician> {
        var shortlist = mutableListOf<Musician>()
        if (musiciansDetail.containsKey(musicianId)) {
            musiciansDetail[musicianId]?.let { shortlist.add(it) }
        }
        return shortlist
    }
}