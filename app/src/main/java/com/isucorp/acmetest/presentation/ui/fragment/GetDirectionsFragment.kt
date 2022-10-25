package com.isucorp.acmetest.presentation.ui.fragment

import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.isucorp.acmetest.R
import com.isucorp.acmetest.databinding.FragmentGetDirectionsBinding
import com.isucorp.acmetest.presentation.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class GetDirectionsFragment : Fragment() {

    private var map: GoogleMap? = null
    private lateinit var binding: FragmentGetDirectionsBinding
    private val mainActivityViewModel: MainActivityViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_get_directions, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = mainActivityViewModel

        subscribeToObservables()
        searchDirections()
        return binding.root
    }

    private val callback = OnMapReadyCallback {
        map = it
        val ticket = mainActivityViewModel.workTicket.value
        val directions = ticket?.jobStreet + "," + ticket?.jobCity
        if (!directions.isNullOrEmpty())
            mainActivityViewModel.geocoding(directions,getString(R.string.google_maps_key))

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    private fun searchDirections() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val address = searchView.query.toString()
                if (address.isEmpty()) return false
                mainActivityViewModel.geocoding(address,getString(R.string.google_maps_key))
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return false
            }

        })
    }

    private fun subscribeToObservables(){
        mainActivityViewModel.latLng.observe(this) {
            moveOnMap(it)
        }
    }

    private fun moveOnMap(latLng: LatLng){
        map?.addMarker(MarkerOptions().position(latLng))
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
    }


}