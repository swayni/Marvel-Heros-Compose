package com.swayni.marvelheroescompose.ui.screen.favorite.viewmodel

import androidx.lifecycle.viewModelScope
import com.swayni.marvelheroescompose.core.base.viewmodel.BaseViewModel
import com.swayni.marvelheroescompose.core.model.ErrorInfoModel
import com.swayni.marvelheroescompose.core.model.ErrorType
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.di.quality.IoDispatcher
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import com.swayni.marvelheroescompose.domain.usecase.FavoriteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUserCase: FavoriteListUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel<List<CharacterModel>>(){

    fun getFavoriteCharacters(){
        viewModelScope.launch(dispatcher) {
            favoriteUserCase.execute().onStart {
                updateLoading(true)
            }.onCompletion {
                updateLoading(false)
            }.collect{data ->
                when(data){
                    is ResultData.Error -> updateErrorInfo(ErrorInfoModel(data.exception.message.toString(), ErrorType.REALM_EXCEPTION))
                    is ResultData.Success -> updateSuccessData(data.data)
                }
            }
        }
    }
}