package com.isucorp.acmetest.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import com.isucorp.acmetest.R
import com.isucorp.acmetest.data.remote.GoogleMapsApiService
import com.isucorp.acmetest.databinding.ActivityMainBinding
import com.isucorp.acmetest.presentation.viewmodel.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        subscribeToObservables()
    }

    private fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = mainActivityViewModel

        val ticketId: Int = intent.getIntExtra("ticket_id", -1)
        if (ticketId != -1)
            mainActivityViewModel.loadWorkTicket(ticketId)

    }

    private fun subscribeToObservables() {
        mainActivityViewModel.navDestination.observe(this) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navHostFragment.navController.navigate(it)
            when (it) {
                R.id.getDirectionsFragment -> {
                    binding.backPlaceText.text = getString(R.string.ticket_text)
                    binding.windowTitleText.text = getString(R.string.get_directions_text)
                    binding.menuBtn.visibility = View.GONE
                    binding.menuText.visibility = View.GONE
                }
                R.id.workTicketFragment -> {
                    binding.backPlaceText.text = getString(R.string.dashboard_text)
                    binding.windowTitleText.text = getString(R.string.ticket_text)
                    binding.menuBtn.visibility = View.VISIBLE
                    binding.menuText.visibility = View.VISIBLE
                }

            }
        }
        lifecycleScope.launchWhenStarted {
            mainActivityViewModel.uiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    is MainActivityViewModel.UiEvent.ShowMessage -> {
                        Toast.makeText(
                            this@MainActivity,
                            getString(uiEvent.resId ?: R.string.unknown_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    fun back(view: View) {
        onBackPressed()
    }

    override fun onBackPressed() {
        when (mainActivityViewModel.navDestination.value) {
            R.id.getDirectionsFragment -> {
                mainActivityViewModel.goToDestination(R.id.workTicketFragment)
            }
            R.id.workTicketFragment -> {
                finish()
            }
        }
    }

}