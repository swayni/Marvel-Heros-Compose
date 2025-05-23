package com.swayni.marvelheroescompose.data.model

import com.google.gson.annotations.SerializedName

data class CreatorList(
    @SerializedName("available") val available : Int,
    @SerializedName("returned") val returned : Int,
    @SerializedName("collectionURI") val collectionURI : String,
    @SerializedName("items") val items : List<CreatorSummary>
)
