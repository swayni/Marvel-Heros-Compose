package com.swayni.marvelheroescompose.data.remote

import com.swayni.marvelheroescompose.BuildConfig
import com.swayni.marvelheroescompose.core.md5
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.Api
import com.swayni.marvelheroescompose.data.model.CharacterDataWrapper
import com.swayni.marvelheroescompose.data.model.ComicDataWrapper
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: Api) : IRemoteDataSource {

    companion object{
         private const val HASH= "${1}${BuildConfig.PRIVATE_KEY}${BuildConfig.API_KEY}"
    }

    private val hash : String by lazy { HASH.md5() }


    override suspend fun getCharacters(offset: Int, limit: Int): ResultData<CharacterDataWrapper>  {
        return try {
            val response = api.getCharacters("1", BuildConfig.API_KEY, offset, limit, hash)
            ResultData.Success(response)
        }catch (e : Exception){
            ResultData.Error(e)
        }
    }

    override suspend fun getComicsByCharacterId(
        characterId: Int,
        startYear: Int,
        limit: Int,
        orderBy: String
    ): ResultData<ComicDataWrapper> {
        return try {
            val response =  api.getComics(characterId, "1", BuildConfig.API_KEY, startYear, limit, orderBy, hash)
            ResultData.Success(response)
        }catch (e : Exception){
            ResultData.Error(e)
        }
    }

}