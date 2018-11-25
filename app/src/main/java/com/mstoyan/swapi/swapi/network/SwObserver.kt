package com.mstoyan.swapi.swapi.network

import retrofit2.Call
import java.lang.Exception

abstract class SwObserver<S> {
    abstract fun onCachedDataLoaded(data: S, call: Call<S>)
    abstract fun onDataLoaded(data: S, call: Call<S>)
    abstract fun onFailedDataLoading(e: Throwable, call: Call<S>)
}