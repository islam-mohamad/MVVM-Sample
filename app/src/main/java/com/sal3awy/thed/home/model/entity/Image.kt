package com.sal3awy.thed.home.model.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Image(
    @SerializedName("link")
    var link: String,
    @SerializedName("height")
    var height: Double,
    @SerializedName("width")
    var width: Double
): Serializable