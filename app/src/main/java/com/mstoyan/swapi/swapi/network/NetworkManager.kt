package com.mstoyan.swapi.swapi.network

import com.mstoyan.swapi.swapi.network.services.SwApi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {

    object Configurator{
        private val client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
        private var retrofit = Retrofit.Builder()
            .baseUrl(
                HttpUrl.Builder()
                    .scheme("https")
                    .host("swapi.co")
                    .addEncodedPathSegments("api/")
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(GsonConfig.networkGson))
            .client(client)
            .build()

        fun <S> createService(serviceClass: Class<S>): S {
            return retrofit.create(serviceClass)
        }
    }


    val swApiService = Configurator.createService(SwApi::class.java)
}