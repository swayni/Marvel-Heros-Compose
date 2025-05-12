package com.swayni.marvelheroescompose.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EventSummary(
    @SerializedName("resourceURI") val resourceURI : String,
    @SerializedName("name") val name : String
): Serializable
