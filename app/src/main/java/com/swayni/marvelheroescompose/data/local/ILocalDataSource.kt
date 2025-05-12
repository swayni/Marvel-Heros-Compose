package com.swayni.marvelheroescompose.data.local

import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.domain.model.CharacterModel

interface ILocalDataSource {

    suspend fun getFavoriteCharacters(): ResultData<List<CharacterModel>>
    suspend fun getFavoriteCharacterWithId(id: Int): ResultData<Boolean>
    suspend fun addFavoriteCharacter(character: CharacterModel) : ResultData<Boolean>
    suspend fun removeFavoriteCharacter(character: CharacterModel): ResultData<Boolean>
}