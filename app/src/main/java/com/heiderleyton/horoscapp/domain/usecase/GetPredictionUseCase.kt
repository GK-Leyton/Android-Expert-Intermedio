package com.heiderleyton.horoscapp.domain.usecase

import com.heiderleyton.horoscapp.domain.Repository
import javax.inject.Inject

class GetPredictionUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(sign:String) = repository.getPrediction(sign)

}