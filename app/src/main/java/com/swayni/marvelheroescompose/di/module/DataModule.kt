package com.swayni.marvelheroescompose.di.module

import com.swayni.marvelheroescompose.data.Api
import com.swayni.marvelheroescompose.data.local.ILocalDataSource
import com.swayni.marvelheroescompose.data.local.LocalDataSource
import com.swayni.marvelheroescompose.data.remote.IRemoteDataSource
import com.swayni.marvelheroescompose.data.remote.RemoteDataSource
import com.swayni.marvelheroescompose.data.repository.IRepository
import com.swayni.marvelheroescompose.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(api: Api): IRemoteDataSource = RemoteDataSource(api)

    @Singleton
    @Provides
    fun provideLocalDataSource(realm: Realm): ILocalDataSource = LocalDataSource(realm)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: IRemoteDataSource, localDataSource: ILocalDataSource): IRepository = Repository(remoteDataSource, localDataSource)
}