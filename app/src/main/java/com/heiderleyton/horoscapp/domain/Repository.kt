package com.heiderleyton.horoscapp.domain

import com.heiderleyton.horoscapp.data.network.response.HoroscopePredictionResponse
import com.heiderleyton.horoscapp.domain.model.PredictionModel

interface Repository {
    suspend fun getPrediction(sign: String) : PredictionModel?
}