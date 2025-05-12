package com.swayni.marvelheroescompose.domain.usecase

import com.swayni.marvelheroescompose.core.base.viewmodel.BaseUseCase
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.model.ComicDataWrapper
import com.swayni.marvelheroescompose.data.repository.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailUseCase @Inject constructor(private val repository: IRepository) : BaseUseCase() {

    suspend fun execute(characterId : Int, startYear : Int, limit:Int, orderBy:String): Flow<ResultData<ComicDataWrapper>> = invoke { repository.getComicsByCharacterId(characterId, startYear, limit, orderBy) }

}