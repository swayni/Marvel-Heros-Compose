package com.swayni.marvelheroescompose.data.repository

import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.model.CharacterDataWrapper
import com.swayni.marvelheroescompose.data.model.ComicDataWrapper
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getCharacters(offset : Int, limit : Int): Flow<ResultData<CharacterDataWrapper>>
    suspend fun getComicsByCharacterId(characterId : Int, startYear : Int, limit:Int, orderBy:String): Flow<ResultData<ComicDataWrapper>>


    suspend fun getFavoriteCharacters() : Flow<ResultData<List<CharacterModel>>>
    suspend fun getFavoriteCharacterWithId(id:Int) : Flow<ResultData<Boolean>>
    suspend fun addFavoriteCharacter(character: CharacterModel) : Flow<ResultData<Boolean>>
    suspend fun removeFavoriteCharacter(character: CharacterModel) : Flow<ResultData<Boolean>>
}