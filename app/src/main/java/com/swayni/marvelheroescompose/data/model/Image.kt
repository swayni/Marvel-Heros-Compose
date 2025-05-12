package com.swayni.marvelheroescompose.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Image(
    @SerializedName("path") val path :String,
    @SerializedName("extension") val extension: String
): Serializable
