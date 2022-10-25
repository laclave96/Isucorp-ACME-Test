package com.isucorp.acmetest.data.remote.datasource

import com.google.android.gms.maps.model.LatLng
import com.isucorp.acmetest.utils.Resource

interface CoordinatesRemoteDataSource {
    suspend fun getCoordinates(address:String, key: String): Resource<LatLng>
}