package com.swayni.marvelheroescompose.domain.usecase

import com.swayni.marvelheroescompose.core.base.viewmodel.BaseUseCase
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.repository.IRepository
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(private val repository: IRepository): BaseUseCase(){

    suspend fun execute(characterModel: CharacterModel): Flow<ResultData<Boolean>> = invoke { repository.addFavoriteCharacter(characterModel) }
}