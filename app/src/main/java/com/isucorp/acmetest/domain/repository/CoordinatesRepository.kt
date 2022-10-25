package com.isucorp.acmetest.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.isucorp.acmetest.utils.Resource

interface CoordinatesRepository {
    suspend fun getCoordinates(address:String, key: String): Resource<LatLng>
}