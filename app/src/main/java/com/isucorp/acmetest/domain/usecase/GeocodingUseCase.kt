package com.isucorp.acmetest.domain.usecase

import com.google.android.gms.maps.model.LatLng
import com.isucorp.acmetest.domain.repository.CoordinatesRepository
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GeocodingUseCase(private val coordinatesRepository: CoordinatesRepository) {

    suspend operator fun invoke(address: String, key: String): Flow<Resource<LatLng>> = flow {
        emit(Resource.Loading<LatLng>())
        emit(coordinatesRepository.getCoordinates(address, key))
    }
}