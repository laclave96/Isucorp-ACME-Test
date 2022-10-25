package com.isucorp.acmetest.data.remote.repository

import com.google.android.gms.maps.model.LatLng
import com.isucorp.acmetest.data.remote.datasource.CoordinatesRemoteDataSource
import com.isucorp.acmetest.domain.repository.CoordinatesRepository
import com.isucorp.acmetest.utils.Resource

class CoordinatesRepositoryImpl(
    private val remoteDatasource: CoordinatesRemoteDataSource
):CoordinatesRepository{
    override suspend fun getCoordinates(address: String, key: String): Resource<LatLng> {
        return remoteDatasource.getCoordinates(address, key)
    }
}