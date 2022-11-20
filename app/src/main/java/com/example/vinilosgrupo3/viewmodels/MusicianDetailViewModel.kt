package com.example.vinilosgrupo3.viewmodels

import android.app.Application
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.example.vinilosgrupo3.models.Musician
import com.example.vinilosgrupo3.network.NetworkServiceAdapter
import com.example.vinilosgrupo3.repositories.MusicianDetailRepository

class MusicianDetailViewModel(application: Application, musicianId: Int) :  AndroidViewModel(application) {

    private val _musiciandetail = MutableLiveData<Musician>()

    val musiciandetail: LiveData<Musician>
        get() = _musiciandetail

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = musicianId

    private val musicianDetailRepository = MusicianDetailRepository(application)
    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        musicianDetailRepository.refreshData(id,{
            _musiciandetail.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val musicianId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MusicianDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MusicianDetailViewModel(app, musicianId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
