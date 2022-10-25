package com.isucorp.acmetest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isucorp.acmetest.R
import com.isucorp.acmetest.data.local.model.WorkTicketEntity
import com.isucorp.acmetest.domain.usecase.WorkTicketUseCase
import com.isucorp.acmetest.presentation.model.WorkTicketItem
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashBoardViewModel(private val workTicketUseCase: WorkTicketUseCase) : ViewModel() {

    init {
        loadWorkTickets()
    }

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _saveSuccess = MutableLiveData(true)
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    private val _workTickets = MutableLiveData<List<WorkTicketItem>>()
    val workTickets: LiveData<List<WorkTicketItem>> = _workTickets

    private val _dueTickets = MutableLiveData<List<Long>>()
    val dueTickets: LiveData<List<Long>> = _dueTickets

    private val _workTicket = MutableLiveData<WorkTicketEntity>()
    val workTicket: LiveData<WorkTicketEntity> = _workTicket

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        data class ShowMessage(val resId: Int? = null) : UiEvent()
    }

    private var defaultJob: Job? = null
    private var loadTicketJob: Job? = null
    private var saveTicketJob: Job? = null

    fun loadWorkTickets() {
        loadTicketJob?.cancel()
        loadTicketJob = viewModelScope.launch(Dispatchers.IO) {
            workTicketUseCase.getListOfWorkTicketsUseCase().onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _loading.postValue(true)
                    }
                    is Resource.Success -> {
                        _loading.postValue(false)
                        result.data?.let { _workTickets.postValue(it) }
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
            }.collect()

        }
    }

    fun loadDueTickets() {
        defaultJob?.cancel()
        defaultJob = viewModelScope.launch(Dispatchers.IO) {
            workTicketUseCase.getDueTicketsUseCase().onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _loading.postValue(true)
                    }
                    is Resource.Success -> {
                        _loading.postValue(false)
                        result.data?.let { _dueTickets.postValue(it) }
                        if (result.data?.isEmpty() == true)
                            _uiEvent.emit(
                                UiEvent.ShowMessage(
                                    R.string.no_due_tickets
                                )
                            )
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


            }.collect()


        }
    }

    fun syncGoogleCalendar() {
        defaultJob?.cancel()
        defaultJob = viewModelScope.launch(Dispatchers.IO) {
        }
    }

    fun insertWorkTicket(workTicketEntity: WorkTicketEntity) {
        saveTicketJob?.cancel()
        saveTicketJob = viewModelScope.launch(Dispatchers.IO) {
            workTicketUseCase.insertWorkTicketUseCase(workTicketEntity).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _loading.postValue(true)
                        _saveSuccess.postValue(false)
                    }
                    is Resource.Success -> {
                        _loading.postValue(false)
                        _saveSuccess.postValue(true)
                        LoginViewModel.UiEvent.ShowMessage(
                            R.string.ticket_successfully_inserted
                        )
                    }
                    is Resource.Error -> {
                        _loading.postValue(false)
                        _saveSuccess.postValue(false)
                        _uiEvent.emit(
                            UiEvent.ShowMessage(
                                result.resId ?: R.string.unknown_error
                            )
                        )
                    }
                }
            }.collect()
        }
    }

    fun updateWorkTicket(workTicketEntity: WorkTicketEntity) {
        saveTicketJob?.cancel()
        saveTicketJob = viewModelScope.launch(Dispatchers.IO) {
            workTicketUseCase.updateWorkTicketUseCase(workTicketEntity).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _loading.postValue(true)
                        _saveSuccess.postValue(false)
                    }
                    is Resource.Success -> {
                        _loading.postValue(false)
                        _saveSuccess.postValue(true)
                        LoginViewModel.UiEvent.ShowMessage(
                            R.string.successfully_updated_ticket
                        )
                    }
                    is Resource.Error -> {
                        _loading.postValue(false)
                        _saveSuccess.postValue(false)
                        _uiEvent.emit(
                            UiEvent.ShowMessage(
                                result.resId ?: R.string.unknown_error
                            )
                        )
                    }
                }
            }.collect()
        }
    }

    fun getWorkTicket(id: Int) {
        defaultJob?.cancel()
        defaultJob = viewModelScope.launch(Dispatchers.IO) {
            when (val result = workTicketUseCase.getWorkTicketUseCase(id)) {
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

    fun deleteTicket(id: Int) {
        defaultJob?.cancel()
        defaultJob = viewModelScope.launch(Dispatchers.IO) {
            workTicketUseCase.deleteWorkTicketUseCase(id).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _loading.postValue(true)
                    }
                    is Resource.Success -> {
                        _loading.postValue(false)
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
            }.collect()
        }
    }


}