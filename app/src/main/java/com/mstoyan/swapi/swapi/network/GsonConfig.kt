package com.mstoyan.swapi.swapi.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonConfig {

    val networkGson: Gson
    val defaultGson: Gson

    init {
        defaultGson = GsonBuilder().create()

        //probably will need to add some parsers in future
        networkGson = GsonBuilder().create()
    }

}