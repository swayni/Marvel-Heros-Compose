package com.swayni.marvelheroescompose.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EventList(
    @SerializedName("available") val available: Int,
    @SerializedName("returned") val returned : Int,
    @SerializedName("collectionURI") val collectionURI:String,
    @SerializedName("items") val items: List<EventSummary>
): Serializable
