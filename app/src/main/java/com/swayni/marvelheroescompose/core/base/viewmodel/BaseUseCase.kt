package com.swayni.marvelheroescompose.core.base.viewmodel

abstract class BaseUseCase {

    suspend operator fun <R> invoke(block: suspend () -> R): R = block()

}