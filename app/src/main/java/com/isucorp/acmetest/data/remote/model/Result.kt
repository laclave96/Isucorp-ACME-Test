package com.isucorp.acmetest.data.remote.model

import com.google.gson.annotations.SerializedName

class Result(
    @SerializedName( "address_components")
    val addressComponents: List<AddressComponent>,
    @SerializedName( "formatted_address")
    val formattedAddress: String,
    val geometry: Geometry,
    @SerializedName( "partial_match")
    val partialMatch: Boolean,
    @SerializedName( "place_id")
    val placeId: String,
    val types: List<String>
) {
}