package com.isucorp.acmetest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.isucorp.acmetest.R
import com.isucorp.acmetest.data.local.model.WorkTicketEntity
import com.isucorp.acmetest.domain.usecase.GeocodingUseCase
import com.isucorp.acmetest.domain.usecase.GetWorkTicketUseCase
import com.isucorp.acmetest.presentation.model.ApproxDistance
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val getWorkTicketUseCase: GetWorkTicketUseCase,
    private val geocodingUseCase: GeocodingUseCase
) : ViewModel() {

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _workTicket = MutableLiveData<WorkTicketEntity>()
    val workTicket: LiveData<WorkTicketEntity> = _workTicket

    private val _navDestination = MutableLiveData<Int>(R.id.workTicketFragment)
    val navDestination: LiveData<Int> = _navDestination

    private val _latLng = MutableLiveData<LatLng>()
    val latLng: LiveData<LatLng> = _latLng

    private val _approxDistance = MutableLiveData<ApproxDistance>()
    val approxDistance: LiveData<ApproxDistance> = _approxDistance

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        data class ShowMessage(val resId: Int? = null) : UiEvent()
    }

    private var loadTicketJob: Job? = null
    private var geocodingJob: Job? = null

    fun loadWorkTicket(id: Int) {
        loadTicketJob?.cancel()
        loadTicketJob = viewModelScope.launch(Dispatchers.IO) {
            when (val result = getWorkTicketUseCase(id)) {
                is Resource.Loading -> {
                    _loading.postValue(true)
                }
                is Resource.Success -> {
                    _loading.postValue(false)
                    result.data?.let { _workTicket.postValue(it) }
                }
                is Resource.Error -> {
                    _loading.postValue(false)
                    _uiEvent.emit(
                        UiEvent.ShowMessage(
                            result.resId ?: R.string.unknown_error
                        )
                    )
                }
            }

        }
    }

    fun geocoding(address: String, key: String) {
        geocodingJob?.cancel()
        geocodingJob = viewModelScope.launch(Dispatchers.IO) {
            geocodingUseCase(address,key).collect { result->
                when (result) {
                    is Resource.Loading -> {
                        _loading.postValue(true)
                    }
                    is Resource.Success -> {
                        _loading.postValue(false)
                        result.data?.let { _latLng.postValue(it) }
                    }
                    is Resource.Error -> {
                        _loading.postValue(false)
                        _uiEvent.emit(
                            UiEvent.ShowMessage(
                                result.resId ?: R.string.unknown_error
                            )
                        )
                    }
                }
            }


        }
    }

    fun goToDestination(resId: Int) {
        _navDestination.value = resId
    }

}