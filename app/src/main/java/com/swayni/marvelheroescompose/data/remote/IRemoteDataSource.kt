package com.swayni.marvelheroescompose.data.remote

import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.model.CharacterDataWrapper
import com.swayni.marvelheroescompose.data.model.ComicDataWrapper

interface IRemoteDataSource {

    suspend fun getCharacters(offset : Int, limit : Int): ResultData<CharacterDataWrapper>
    suspend fun getComicsByCharacterId(characterId : Int, startYear : Int, limit:Int, orderBy:String): ResultData<ComicDataWrapper>
}