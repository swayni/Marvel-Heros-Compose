package com.swayni.marvelheroescompose.data.local

import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import io.realm.kotlin.Realm
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val realm : Realm) : ILocalDataSource {

    override suspend fun getFavoriteCharacters(): ResultData<List<CharacterModel>> {
        try {
            val results = realm.query(CharacterModel::class).find()
            val characterList = mutableListOf<CharacterModel>()
            results.forEach {
                characterList.add(it)
            }
            return ResultData.Success(characterList)
        }catch (e : Exception){
            return ResultData.Error(e)
        }
    }

    override suspend fun getFavoriteCharacterWithId(id: Int): ResultData<Boolean> {
        try {
            val result = realm.query(CharacterModel::class, "id == $0", id).first().find()
            return if(result != null){
                ResultData.Success(true)
            }else {
                ResultData.Success(false)
            }
        }catch (e : Exception){
            return ResultData.Error(e)
        }
    }

    override suspend fun addFavoriteCharacter(character: CharacterModel): ResultData<Boolean> {
        try {
             realm.writeBlocking {
                copyToRealm(character)
            }
            return ResultData.Success(true)
        }catch (e : Exception){
            return ResultData.Success(false)
        }
    }

    override suspend fun removeFavoriteCharacter(character: CharacterModel): ResultData<Boolean> {
        try {
            realm.writeBlocking {
                val result = query(CharacterModel::class, "id == $0", character.id).first().find()
                result?.let {
                    delete(it)
                }
            }
            return ResultData.Success(true)
        }catch (e: Exception){
            return ResultData.Success(false)
        }
    }
}