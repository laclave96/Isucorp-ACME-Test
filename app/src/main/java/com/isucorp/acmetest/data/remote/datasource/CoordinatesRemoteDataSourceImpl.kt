package com.isucorp.acmetest.data.remote.datasource

import com.google.android.gms.maps.model.LatLng
import com.isucorp.acmetest.R
import com.isucorp.acmetest.data.remote.GoogleMapsApiService
import com.isucorp.acmetest.data.remote.model.Result
import com.isucorp.acmetest.utils.Resource



class CoordinatesRemoteDataSourceImpl(
    private val service: GoogleMapsApiService
) : CoordinatesRemoteDataSource {

    override suspend fun getCoordinates(address: String, key: String): Resource<LatLng> {
        val response = service.getLocationInfo(address, key)

        if (!response.isSuccessful || response.body() == null)
            return Resource.Error(resId = R.string.error_finding_address)
        val result: Result = response.body()?.results?.get(0)
            ?:return Resource.Error(resId = R.string.error_finding_address)

        val location = result.geometry.location
        return Resource.Success(LatLng(location.lat,location.lng))
    }
}
