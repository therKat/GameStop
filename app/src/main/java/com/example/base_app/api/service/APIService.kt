package com.example.base_app.api.service

import com.example.base_app.data.model.GameModel
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
//    @GET("character")
//    suspend fun getCharacter(@Query("page") page: Int) : ResponseApi

    @GET("games")
    fun getGames(): Call<Map<String, GameModel>>


}