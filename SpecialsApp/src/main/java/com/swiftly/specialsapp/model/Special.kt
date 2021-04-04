package com.swiftly.specialsapp.model

import com.google.gson.annotations.SerializedName

class Special {
    var imageUrl: String? = null
    var width: UInt? = null
    var height: UInt? = null
    @SerializedName("display_name")
    var displayName: String? = null
    @SerializedName("original_price")
    var originalPrice: String? = null
    var price: String? = null
}