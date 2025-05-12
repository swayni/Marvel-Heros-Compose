package com.swayni.marvelheroescompose.domain.usecase

import com.swayni.marvelheroescompose.core.base.viewmodel.BaseUseCase
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.repository.Repository
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteListUseCase @Inject constructor(private val repository: Repository) : BaseUseCase() {

    suspend fun execute() : Flow<ResultData<List<CharacterModel>>> = invoke { repository.getFavoriteCharacters() }
}