package com.example.financeapp.di

import android.app.Application
import android.content.Context
import com.example.financeapp.api.ApiService
import com.example.financeapp.api.TokenInterceptor
import com.example.financeapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideOkhttp(tokenInterceptor: TokenInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(tokenInterceptor)
        return client.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(Constants.URL)
            .client(okHttpClient).build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
