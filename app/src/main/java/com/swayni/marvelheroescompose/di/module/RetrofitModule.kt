package com.swayni.marvelheroescompose.di.module

import com.swayni.marvelheroescompose.data.Api
import com.swayni.marvelheroescompose.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder().readTimeout(1200, TimeUnit.SECONDS).connectTimeout(1200,
        TimeUnit.SECONDS).build()

    @Singleton
    @Provides
    fun apiCreator(client: OkHttpClient): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addCallAdapterFactory(
        RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(client).build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}