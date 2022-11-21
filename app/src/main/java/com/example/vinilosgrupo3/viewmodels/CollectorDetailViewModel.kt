package com.example.vinilosgrupo3.viewmodels

import android.app.Application
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.example.vinilosgrupo3.models.Collector
import com.example.vinilosgrupo3.network.NetworkServiceAdapter
import com.example.vinilosgrupo3.repositories.CollectorRepository
import com.example.vinilosgrupo3.repositories.CollectorDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorDetailViewModel(application: Application, collectorId: Int) :  AndroidViewModel(application) {

    private val _collectorDetail = MutableLiveData<Collector>()

    val collectorDetail: LiveData<Collector>
        get() = _collectorDetail

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = collectorId

    private val collectorDetailRepository = CollectorDetailRepository(application)
    init {
        refreshDataFromNetwork()
    }



    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = collectorDetailRepository.refreshData(id)
                    _collectorDetail.postValue(data)
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

    class Factory(val app: Application, val collectorId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorDetailViewModel(app, collectorId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}
