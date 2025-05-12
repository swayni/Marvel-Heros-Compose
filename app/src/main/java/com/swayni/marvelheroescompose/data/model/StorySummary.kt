package com.swayni.marvelheroescompose.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StorySummary(
    @SerializedName("resourceURI") val resourceURI : String,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String
): Serializable
