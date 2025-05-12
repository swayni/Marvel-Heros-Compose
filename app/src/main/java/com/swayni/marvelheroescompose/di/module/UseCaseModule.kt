package com.swayni.marvelheroescompose.di.module

import com.swayni.marvelheroescompose.core.base.viewmodel.BaseUseCase
import com.swayni.marvelheroescompose.data.repository.IRepository
import com.swayni.marvelheroescompose.domain.usecase.DetailUseCase
import com.swayni.marvelheroescompose.domain.usecase.ListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideListUseCase(repository: IRepository) : BaseUseCase = ListUseCase(repository)


    @Singleton
    @Provides
    fun provideDetailUseCase(repository: IRepository) : BaseUseCase = DetailUseCase(repository)
}