package com.test.superhero.io.response

import com.google.gson.annotations.SerializedName

data class HeroResponse(
    @SerializedName("response") val response: String,
    @SerializedName("response_for") val response_for: String,
    @SerializedName("results") val heros: List<String>
)