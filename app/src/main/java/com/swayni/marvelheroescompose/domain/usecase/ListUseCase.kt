package com.swayni.marvelheroescompose.domain.usecase

import com.swayni.marvelheroescompose.core.base.viewmodel.BaseUseCase
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.model.CharacterDataWrapper
import com.swayni.marvelheroescompose.data.repository.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListUseCase @Inject constructor(private val repository: IRepository) : BaseUseCase() {

    suspend fun execute(offset : Int, limit : Int) : Flow<ResultData<CharacterDataWrapper>> = invoke { repository.getCharacters(offset, limit) }

}