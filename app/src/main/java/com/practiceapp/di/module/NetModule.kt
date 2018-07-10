package com.practiceapp.di.module

import com.practiceapp.utils.TIMEOUT
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule(private val baseURL: String) {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(TIMEOUT, TimeUnit.SECONDS)
        httpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS)

        val client = httpClient.build()

        return Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }
}