package com.isucorp.acmetest.data.remote


import com.isucorp.acmetest.data.remote.model.ResponseApi
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


import retrofit2.http.Query

interface GoogleMapsApiService {
    @GET("maps/api/geocode/json")
    suspend fun getLocationInfo(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Response<ResponseApi>
}