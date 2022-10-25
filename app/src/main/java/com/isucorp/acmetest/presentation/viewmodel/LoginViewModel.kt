package com.isucorp.acmetest.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.isucorp.acmetest.R
import com.isucorp.acmetest.data.local.model.LoginCredentials
import com.isucorp.acmetest.domain.usecase.LoginUseCase
import com.isucorp.acmetest.presentation.LoginResult
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _loginResult = MutableLiveData(LoginResult())
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _loggedIn = MutableLiveData(false)
    val loggedIn: LiveData<Boolean> = _loggedIn

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        data class ShowMessage(val resId: Int? = null) : UiEvent()
    }

    private var loginJob: Job? = null
    private var fakeCredentialsJob: Job? = null

    fun login(loginCredentials: LoginCredentials) {
        loginJob?.cancel()
        loginJob = viewModelScope.launch(Dispatchers.IO) {
            loginUseCase(loginCredentials).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _loading.postValue(true)
                        _loginResult.postValue(result.data)
                    }
                    is Resource.Success -> {
                        _loading.postValue(false)
                        _loginResult.postValue(result.data)
                        _loggedIn.postValue(true)

                    }
                    is Resource.Error -> {
                        _loading.postValue(false)
                        _loginResult.postValue(result.data)
                        _uiEvent.emit(UiEvent.ShowMessage(
                            result.resId?: R.string.unknown_error))
                    }
                }
            }
        }

    }

    fun generateFakeCredentials(){
        fakeCredentialsJob?.cancel()
        fakeCredentialsJob = viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.generateFakeCredentials().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("_Login","Fake Credentials: Success")
                    }
                    is Resource.Error -> {
                        val errorId = R.string.unknown_error
                        _uiEvent.emit(UiEvent.ShowMessage(result.resId?:errorId))
                    }
                }
            }.collect()
        }
    }

}