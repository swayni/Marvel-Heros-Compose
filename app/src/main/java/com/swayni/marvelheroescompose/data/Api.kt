package com.swayni.marvelheroescompose.data

import com.swayni.marvelheroescompose.data.model.CharacterDataWrapper
import com.swayni.marvelheroescompose.data.model.ComicDataWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET(value = "characters")
    suspend fun getCharacters(
        @Query("ts") ts:String,
        @Query("apikey") apikey: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("hash") hash : String
    ): CharacterDataWrapper


    @GET(value = "characters/{character_id}/comics")
    suspend fun getComics(
        @Path("character_id") characterId: Int,
        @Query("ts") ts:String,
        @Query("apikey") apikey: String,
        @Query("startYear") startYear: Int,
        @Query("limit") limit: Int,
        @Query("orderBy") orderBy: String,
        @Query("hash") hash : String
    ): ComicDataWrapper

}