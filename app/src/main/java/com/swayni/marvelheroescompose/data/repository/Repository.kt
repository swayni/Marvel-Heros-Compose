package com.swayni.marvelheroescompose.data.repository

import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.local.ILocalDataSource
import com.swayni.marvelheroescompose.data.model.CharacterDataWrapper
import com.swayni.marvelheroescompose.data.model.ComicDataWrapper
import com.swayni.marvelheroescompose.data.remote.IRemoteDataSource
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) : IRepository {

    override suspend fun getCharacters(
        offset: Int,
        limit: Int
    ): Flow<ResultData<CharacterDataWrapper>> = flow {
        emit(remoteDataSource.getCharacters(offset, limit))
    }

    override suspend fun getComicsByCharacterId(
        characterId: Int,
        startYear: Int,
        limit: Int,
        orderBy: String
    ): Flow<ResultData<ComicDataWrapper>> = flow {
        emit(remoteDataSource.getComicsByCharacterId(characterId, startYear, limit, orderBy))
    }

    override suspend fun getFavoriteCharacters(): Flow<ResultData<List<CharacterModel>>> = flow{
        emit(localDataSource.getFavoriteCharacters())
    }

    override suspend fun getFavoriteCharacterWithId(id: Int): Flow<ResultData<Boolean>> = flow {
        emit(localDataSource.getFavoriteCharacterWithId(id))
    }

    override suspend fun addFavoriteCharacter(character: CharacterModel): Flow<ResultData<Boolean>> = flow {
        emit(localDataSource.addFavoriteCharacter(character))
    }

    override suspend fun removeFavoriteCharacter(character: CharacterModel): Flow<ResultData<Boolean>> = flow {
        emit(localDataSource.removeFavoriteCharacter(character))
    }
}