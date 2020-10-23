package com.example.mathfacts

import retrofit2.http.GET

interface MathFactService {
    @GET("/random/math?json")
    suspend fun getMathFact() : MathFact
}