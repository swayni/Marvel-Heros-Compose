package com.swayni.marvelheroescompose.core.base.viewmodel

import androidx.lifecycle.ViewModel
import com.swayni.marvelheroescompose.core.model.ErrorInfoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel <T> : ViewModel() {

    private val _errorInfo = MutableStateFlow<ErrorInfoModel?>(null)
    val errorInfo = _errorInfo.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _successData = MutableStateFlow<T?>(null)
    val successData = _successData.asStateFlow()

    fun updateErrorInfo(errorInfo: ErrorInfoModel?) {
        _errorInfo.update { errorInfo }
    }

    fun updateLoading(loading: Boolean) {
        _loading.update { loading }
    }

    fun updateSuccessData(successData: T?) {
        _successData.update { successData }
    }
}