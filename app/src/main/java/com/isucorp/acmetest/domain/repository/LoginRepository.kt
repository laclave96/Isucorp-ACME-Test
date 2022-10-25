package com.isucorp.acmetest.domain.repository
import com.isucorp.acmetest.data.local.model.LoginCredentials
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun insertCredentials(credentials: LoginCredentials): Flow<Resource<LoginCredentials>>

    fun retrieveCredentials(): Flow<Resource<LoginCredentials>>

    fun updateCredentials(credentials: LoginCredentials): Flow<Resource<LoginCredentials>>

    fun deleteCredentials(): Flow<Resource<LoginCredentials>>

}