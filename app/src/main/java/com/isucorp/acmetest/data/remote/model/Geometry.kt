package com.isucorp.acmetest.data.remote.model

import com.google.gson.annotations.SerializedName

class Geometry(
    val bounds: Bounds,
    val location: Location,
    @SerializedName( "location_type")
    val locationType: String,
    val viewport: ViewPort
) {
}