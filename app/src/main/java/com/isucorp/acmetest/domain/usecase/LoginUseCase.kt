package com.isucorp.acmetest.domain.usecase

import com.isucorp.acmetest.R
import com.isucorp.acmetest.data.local.model.LoginCredentials
import com.isucorp.acmetest.domain.repository.LoginRepository
import com.isucorp.acmetest.presentation.LoginResult
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.*

class LoginUseCase(private val loginRepository: LoginRepository) {

    operator fun invoke(loginCredentials: LoginCredentials):
            Flow<Resource<LoginResult>> = flow {

        emit(Resource.Loading(LoginResult()))

        //artificial delay
        kotlinx.coroutines.delay(1000)

        loginRepository.retrieveCredentials().collect {
            if (it is Resource.Success) {

                if (it.data?.username != loginCredentials.username) {
                    emit(
                        Resource.Error(
                            data = LoginResult(
                                usernameError = R.string.match_username_login_credentials_error
                            ),
                            resId = R.string.match_login_credentials_error
                        )
                    )
                    return@collect
                }
                if (it.data.password != loginCredentials.password) {
                    emit(
                        Resource.Error(
                            data = LoginResult(
                                passwordError = R.string.match_password_login_credentials_error
                            ),
                            resId = R.string.match_login_credentials_error
                        )
                    )
                    return@collect
                }
                emit(Resource.Success(LoginResult()))
            } else {
                emit(
                    Resource.Error<LoginResult>(
                        resId = it.resId ?: R.string.unknown_error
                    )
                )
            }

        }

    }

    fun generateFakeCredentials(): Flow<Resource<LoginCredentials>> = flow {
        //Login FakeCredentials
        val loginCredentials = LoginCredentials("username", "123asd")
        loginRepository.insertCredentials(loginCredentials).onEach { emit(it) }.collect()
    }

}