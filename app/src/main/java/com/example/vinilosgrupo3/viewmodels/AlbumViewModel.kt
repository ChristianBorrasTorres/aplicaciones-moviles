package com.example.vinilosgrupo3.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vinilosgrupo3.R
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.repositories.AlbumRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AlbumViewModel(application: Application) :  AndroidViewModel(application) {


    var nameAlbum: MutableLiveData<String> = MutableLiveData()
    /*val nameAlbum: LiveData<String>
        get() = _nameAlbum*/

    var coverAlbum: MutableLiveData<String> = MutableLiveData()
    var releaseDateAlbum: MutableLiveData<String> = MutableLiveData()
    var descriptionAlbum: MutableLiveData<String> = MutableLiveData()
    var genreAlbum: MutableLiveData<String> = MutableLiveData()
    var recordLabelAlbum: MutableLiveData<String> = MutableLiveData()

    private var albumMutableLiveData: MutableLiveData<Album?>? = null

    fun getAlbumData(): MutableLiveData<Album?>? {
        if (albumMutableLiveData == null) {
            albumMutableLiveData = MutableLiveData()
        }
        return albumMutableLiveData
    }

    private val _albums = MutableLiveData<List<Album>>()

    val albums: LiveData<List<Album>>
        get() = _albums

    private val _album = MutableLiveData<Album>()

    val album: LiveData<Album>
        get() = _album

    private var _createAlbum = MutableLiveData<Boolean>(false)

    val createAlbum: LiveData<Boolean>
        get() = _createAlbum


    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError


    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val albumRepository = AlbumRepository(application)


    init {
        refreshDataFromNetwork()
    }

    /*private fun refreshDataFromNetwork() {
        albumRepository.refreshData({
            _albums.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }*/
    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = albumRepository.refreshData()
                    _albums.postValue(data)
                }
                withContext(Dispatchers.IO){
                    var data = albumRepository.refreshData()
                    _albums.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun createAlbumFromNetwork(album: JSONObject) {
        var id:Int=0
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = albumRepository.createAlbum(album)
                    _album.postValue(data)
                    Log.d("Args", "response createAlbum=$data")
                    id=data.albumId
                    _createAlbum.postValue(true)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
                _createAlbum.postValue(false)
            }
        }
        catch (e:Exception){
            Log.d("Args", "response createAlbum error=$e")
            _eventNetworkError.value = true
        }
        //return id
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
