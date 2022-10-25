package com.isucorp.acmetest.data.local.repository

import com.isucorp.acmetest.data.local.model.LoginCredentials
import com.isucorp.acmetest.data.local.datasource.LoginCredentialsLocalDatasource
import com.isucorp.acmetest.domain.repository.LoginRepository
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class LoginRepositoryImpl(
    private val localDatasource: LoginCredentialsLocalDatasource
) : LoginRepository {

    override fun insertCredentials(credentials: LoginCredentials):
            Flow<Resource<LoginCredentials>> = flow {
        localDatasource.insertCredentials(credentials).onEach { emit(it) }.collect()
    }

    override fun retrieveCredentials(): Flow<Resource<LoginCredentials>> = flow {
        localDatasource.getCredentials().onEach { emit(it) }.collect()
    }

    override fun updateCredentials(credentials: LoginCredentials):
            Flow<Resource<LoginCredentials>> = flow {
        localDatasource.updateCredentials(credentials).onEach { emit(it) }.collect()
    }

    override fun deleteCredentials():
            Flow<Resource<LoginCredentials>> = flow {
        localDatasource.deleteCredentials().onEach { emit(it) }.collect()
    }

}