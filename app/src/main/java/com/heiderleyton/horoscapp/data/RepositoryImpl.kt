package com.heiderleyton.horoscapp.data

import com.heiderleyton.horoscapp.data.network.HoroscopeApiService
import com.heiderleyton.horoscapp.data.network.response.HoroscopePredictionResponse
import com.heiderleyton.horoscapp.domain.Repository
import com.heiderleyton.horoscapp.domain.model.PredictionModel
import retrofit2.Retrofit
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: HoroscopeApiService):Repository {

    override suspend fun getPrediction(sign: String): PredictionModel? {
        //PeticionRetrofit

        runCatching { apiService.getHoroscope(sign) }
            .onSuccess { return it.toDomain() }
            .onFailure { return null }

        return null
    }


}