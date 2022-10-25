package com.isucorp.acmetest.data.local.datasource

import com.isucorp.acmetest.data.local.model.LoginCredentials
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class LoginCredentialsLocalDatasourceImpl(
    private val dataObjectStorage: DataObjectStorage<LoginCredentials>
) : LoginCredentialsLocalDatasource {

    override fun insertCredentials(credentials: LoginCredentials):
            Flow<Resource<LoginCredentials>> = flow {
        dataObjectStorage.saveData(credentials).onEach { emit(it) }.collect()
    }

    override fun getCredentials(): Flow<Resource<LoginCredentials>> = flow {
        dataObjectStorage.getData().onEach { emit(it) }.collect()
    }

    override fun updateCredentials(credentials: LoginCredentials):
            Flow<Resource<LoginCredentials>> = flow{
        dataObjectStorage.saveData(credentials).onEach { emit(it) }.collect()
    }

    override fun deleteCredentials(): Flow<Resource<LoginCredentials>> = flow{
        dataObjectStorage.clear().onEach { emit(it) }.collect()
    }


}