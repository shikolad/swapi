package com.mstoyan.swapi.swapi.network

import com.mstoyan.swapi.swapi.network.services.Man
import com.mstoyan.swapi.swapi.network.services.SearchAnswer
import com.mstoyan.swapi.swapi.network.services.SwApi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {

    private object Configurator{
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

    private object Observers{
        val searchPeopleSearchObservers = ArrayList<SwObserver<SearchAnswer<Man>>>()
    }

    private object Callbacks{
        val searchPeopleCallback = object: Callback<SearchAnswer<Man>>{
            override fun onFailure(call: Call<SearchAnswer<Man>>, t: Throwable) {
                for (observer in Observers.searchPeopleSearchObservers)
                    observer.onFailedDataLoading(t, call)
            }

            override fun onResponse(
                call: Call<SearchAnswer<Man>>,
                response: Response<SearchAnswer<Man>>
            ) {
                val result = response.body()
                if (result == null){
                    onFailure(call, NullPointerException("Empty answer!"))
                } else
                    for (observer in Observers.searchPeopleSearchObservers)
                        observer.onDataLoaded(result, call)
            }

        }
    }

    object ApiCalls{
        fun search(search: String, page: Int = 1){
            swApiService.searchPeopleByName(search, page).enqueue(Callbacks.searchPeopleCallback)
        }
    }

    private val swApiService = Configurator.createService(SwApi::class.java)

    fun registerPeopleSearchObserver(observer: SwObserver<SearchAnswer<Man>>){
        Observers.searchPeopleSearchObservers.add(observer)
    }

    fun unregisterPeopleSearchObserver(observer: SwObserver<SearchAnswer<Man>>){
        Observers.searchPeopleSearchObservers.remove(observer)
    }
}