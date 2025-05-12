package com.swayni.marvelheroescompose.ui.screen.detail.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.swayni.marvelheroescompose.core.base.viewmodel.BaseViewModel
import com.swayni.marvelheroescompose.core.model.ErrorInfoModel
import com.swayni.marvelheroescompose.core.model.ErrorType
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.model.Comic
import com.swayni.marvelheroescompose.domain.usecase.DetailUseCase
import com.swayni.marvelheroescompose.di.quality.IoDispatcher
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import com.swayni.marvelheroescompose.domain.usecase.AddFavoriteUseCase
import com.swayni.marvelheroescompose.domain.usecase.CheckFavoriteUseCase
import com.swayni.marvelheroescompose.domain.usecase.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCase: DetailUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher): BaseViewModel<List<Comic>>() {

    private var startYear : Int = 2005
    private val limit : Int = 10
    private val orderBy : String = "onsaleDate"

    private val _uiIsFavoriteState = MutableStateFlow(false)
    val uiIsFavoriteState = _uiIsFavoriteState.asStateFlow()

    fun getComics(characterId: Int){
        viewModelScope.launch(dispatcher) {
            Log.e("DetailViewModel", "getComics: $characterId")
            detailUseCase.execute(characterId, startYear, limit, orderBy)
                .onStart {
                    updateLoading(true)
                }
                .onCompletion {
                    updateLoading(false)
                }
                .collect{ data ->
                    when(data){
                        is ResultData.Error -> {
                            updateErrorInfo(ErrorInfoModel(data.exception.message.toString(), ErrorType.HTTP))
                        }
                        is ResultData.Success -> {
                            updateSuccessData(data.data.data.results)
                        }
                    }
                }
        }
    }

    fun checkFavorite(characterModel: CharacterModel){
        viewModelScope.launch(dispatcher) {
            checkFavoriteUseCase.execute(characterModel)
                .onStart {
                    updateLoading(true)
                }.onCompletion {
                    updateLoading(false)
                }.collect{ data ->
                    when(data){
                        is ResultData.Error -> updateErrorInfo(ErrorInfoModel(data.exception.message.toString(), ErrorType.REALM_EXCEPTION))
                        is ResultData.Success -> {
                            _uiIsFavoriteState.update {
                                data.data
                            }
                        }

                    }
                }
        }
    }
    fun addFavorite(characterModel: CharacterModel){
        viewModelScope.launch(dispatcher) {
            addFavoriteUseCase.execute(characterModel)
                .onStart {
                    updateLoading(true)
                }.onCompletion {
                    updateLoading(false)
                }.collect{ data ->
                    when(data){
                        is ResultData.Error -> updateErrorInfo(ErrorInfoModel(data.exception.message.toString(), ErrorType.REALM_EXCEPTION))
                        is ResultData.Success -> {
                            _uiIsFavoriteState.update {
                                data.data
                            }
                        }
                    }
                }
        }
    }

    fun removeFavorite(characterModel: CharacterModel){
        viewModelScope.launch(dispatcher) {
            removeFavoriteUseCase.execute(characterModel)
                .onStart {
                    updateLoading(true)
                }.onCompletion {
                    updateLoading(false)
                }.collect { data ->
                    when (data) {
                        is ResultData.Error -> updateErrorInfo(
                            ErrorInfoModel(
                                data.exception.message.toString(),
                                ErrorType.REALM_EXCEPTION
                            )
                        )

                        is ResultData.Success -> {
                            _uiIsFavoriteState.update {
                                data.data
                            }
                        }
                    }
                }
        }
    }
}