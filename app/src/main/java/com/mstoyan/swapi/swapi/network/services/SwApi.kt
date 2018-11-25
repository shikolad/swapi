package com.mstoyan.swapi.swapi.network.services

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SwApi {

    class SearchAnswer<S>{
        @SerializedName("count")
        var count: Int = 0
        @SerializedName("next")
        var nextLink: String? = null
        @SerializedName("previous")
        var prevLink: String? = null
        @SerializedName("results")
        var result: Array<S>? = null
    }

    class Man{
        @SerializedName("name")
        var name = ""
        @SerializedName("birth_year")
        var birth_year = ""
        @SerializedName("eye_color")
        var eye_color = ""
        @SerializedName("gender")
        var gender = ""
        @SerializedName("hair_color")
        var hair_color = ""
        @SerializedName("height")
        var height = ""
        @SerializedName("mass")
        var mass = ""
        @SerializedName("skin_color")
        var skin_color = ""
        @SerializedName("homeworld")
        var homeworld = ""
        @SerializedName("url")
        var url = ""
        @SerializedName("created")
        var created = ""
        @SerializedName("edited")
        var edited = ""
    }

    @GET("people/")
    fun searchPeopleByName(@Query("search") name: String,
                           @Query("page") page: Int = 1,
                           @Query("format") resultType: String = "json"): Call<SearchAnswer<Man>>
}