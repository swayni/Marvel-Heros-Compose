package com.swayni.marvelheroescompose.di.module

import com.swayni.marvelheroescompose.BuildConfig
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Singleton
    @Provides
    fun realmConfiguration() : RealmConfiguration = RealmConfiguration.Builder(schema = setOf(
        CharacterModel::class)).name(BuildConfig.DATABASE_NAME).compactOnLaunch().schemaVersion(1).build()

    @Singleton
    @Provides
    fun realm(realmConfiguration: RealmConfiguration) : Realm = Realm.open(realmConfiguration)
}