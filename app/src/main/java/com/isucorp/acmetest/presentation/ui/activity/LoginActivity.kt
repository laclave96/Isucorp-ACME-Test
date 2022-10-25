package com.isucorp.acmetest.presentation.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.isucorp.acmetest.R
import com.isucorp.acmetest.data.local.model.LoginCredentials

import com.isucorp.acmetest.databinding.ActivityLoginBinding
import com.isucorp.acmetest.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        loginViewModel.generateFakeCredentials()
        subscribeToObservables()
    }

    private fun init() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = loginViewModel
    }

    private fun subscribeToObservables() {
        loginViewModel.loginResult.observe(this) { result ->
            binding.editTextUsername.error = result.usernameError?.let { getString(it) }
            binding.editTextPassword.error = result.passwordError?.let { getString(it) }
        }

        loginViewModel.loggedIn.observe(this) {
            if (it == true) {
                intent = Intent(this, DashBoardActivity::class.java)
                startActivity(intent)
            }

        }

        lifecycleScope.launchWhenStarted {
            loginViewModel.uiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    is LoginViewModel.UiEvent.ShowMessage -> {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(uiEvent.resId ?: R.string.unknown_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

        }

    }


    fun login(view: View) {
        loginViewModel.login(
            LoginCredentials(
                binding.editTextUsername.text.toString().trim(),
                binding.editTextPassword.text.toString().trim()
            )
        )
    }
}