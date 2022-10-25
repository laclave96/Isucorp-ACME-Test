package com.isucorp.acmetest.data.local.datasource

import com.isucorp.acmetest.data.local.model.LoginCredentials
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LoginCredentialsLocalDatasource {

    fun insertCredentials(credentials: LoginCredentials): Flow<Resource<LoginCredentials>>

    fun getCredentials(): Flow<Resource<LoginCredentials>>

    fun updateCredentials(credentials: LoginCredentials): Flow<Resource<LoginCredentials>>

    fun deleteCredentials(): Flow<Resource<LoginCredentials>>
}