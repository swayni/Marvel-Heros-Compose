package com.swayni.marvelheroescompose.ui.screen.list.viewmodel

import androidx.lifecycle.viewModelScope
import com.swayni.marvelheroescompose.core.base.viewmodel.BaseViewModel
import com.swayni.marvelheroescompose.core.model.ErrorInfoModel
import com.swayni.marvelheroescompose.core.model.ErrorType
import com.swayni.marvelheroescompose.core.model.ResultData
import com.swayni.marvelheroescompose.data.model.Character
import com.swayni.marvelheroescompose.di.quality.IoDispatcher
import com.swayni.marvelheroescompose.domain.usecase.ListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(private val useCase : ListUseCase, @IoDispatcher private val dispatcher: CoroutineDispatcher) : BaseViewModel<List<Character>>() {

    private var offset : Int = 0
    private var limit : Int = 20
    private var maxOffset : Int = 20

    private val charactersList = ArrayList<Character>()
    private var isLoading = false

    fun getCharacters(isLoadMore : Boolean = false) {
        viewModelScope.launch (dispatcher) {
            useCase.execute(offset = offset, limit = limit)
                .onStart {
                    if (!isLoadMore){
                        offset = 0
                        charactersList.clear()
                    }
                    isLoading = true
                    updateLoading(isLoading)
                }.onCompletion {
                    isLoading = false
                    updateLoading(isLoading)
                }
                .collect{ data ->
                    when(data){
                        is ResultData.Error -> {
                            updateErrorInfo(ErrorInfoModel(data.exception.message.toString(), ErrorType.HTTP))
                        }
                        is ResultData.Success -> {
                            maxOffset = data.data.data.total / data.data.data.limit
                            charactersList.addAll(data.data.data.results)
                            updateSuccessData(charactersList.map { character -> character })
                        }
                    }
                }
        }
    }

    fun loadMoreCharacters(character: Character) {
        if (!isLoading && offset <= 78) {
            if (charactersList.last() == character) {
                offset++
                getCharacters(isLoadMore = true)
            }
        }
    }
}