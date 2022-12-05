package com.example.vinilosgrupo3.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilosgrupo3.models.Track
import com.example.vinilosgrupo3.repositories.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class TrackViewModel(application: Application, albumId: Int) :  AndroidViewModel(application) {

    var nameTrack: MutableLiveData<String> = MutableLiveData()
    var durationTrack: MutableLiveData<String> = MutableLiveData()

    private var trackMutableLiveData: MutableLiveData<Track?>? = null

    fun getTrackData(): MutableLiveData<Track?>? {
        if (trackMutableLiveData == null) {
            trackMutableLiveData = MutableLiveData()
        }
        return trackMutableLiveData
    }

    private val _tracks = MutableLiveData<List<Track>>()

    val tracks: LiveData<List<Track>>
        get() = _tracks

    private val _track = MutableLiveData<Track>()

    val track: LiveData<Track>
        get() = _track

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val trackRepository = TrackRepository(application)

    val albumId = albumId


    init {
    }


    fun addTrackFromNetwork(track: JSONObject):Int {
        var id:Int=0
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = trackRepository.addTrack(albumId, track)
                    _track.postValue(data)
                    id=data.id
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
        return id
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TrackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TrackViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}