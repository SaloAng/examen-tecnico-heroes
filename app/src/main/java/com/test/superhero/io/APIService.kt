package com.test.superhero.io

import com.test.superhero.io.response.HeroResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    fun getHerosById(@Url url:String): Response<HeroResponse>
}