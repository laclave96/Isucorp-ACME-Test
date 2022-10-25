package com.isucorp.acmetest.presentation


data class LoginResult(
    val usernameError: Int? = null,
    val passwordError: Int? = null
) {
}