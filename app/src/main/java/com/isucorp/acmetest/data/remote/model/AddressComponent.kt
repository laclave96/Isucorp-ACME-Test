package com.isucorp.acmetest.data.remote.model

import com.google.gson.annotations.SerializedName

class AddressComponent(
    @SerializedName("long_name")
    val longName: String,
    @SerializedName("short_name")
    val shortName: String,
    val types: List<String>
) {
}