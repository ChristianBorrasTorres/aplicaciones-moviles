package com.example.vinilosgrupo3.viewmodels

import android.app.Application
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.example.vinilosgrupo3.models.Album
import com.example.vinilosgrupo3.network.NetworkServiceAdapter
import com.example.vinilosgrupo3.repositories.AlbumRepository
import com.example.vinilosgrupo3.repositories.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(application: Application, albumId: Int) :  AndroidViewModel(application) {

    private val _detail = MutableLiveData<Album>()

    val detail: LiveData<Album>
        get() = _detail

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = albumId

    private val detailRepository = DetailRepository(application)
    init {
        refreshDataFromNetwork()
    }

    /*private fun refreshDataFromNetwork() {
        detailRepository.refreshData(id,{
            _detail.postValue(it)
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
                    var data = detailRepository.refreshData(id)
                    _detail.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
