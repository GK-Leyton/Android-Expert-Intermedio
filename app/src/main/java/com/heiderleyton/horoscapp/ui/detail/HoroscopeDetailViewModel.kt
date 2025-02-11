package com.heiderleyton.horoscapp.ui.detail

import androidx.lifecycle.ViewModel
import com.heiderleyton.horoscapp.domain.usecase.GetPredictionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.heiderleyton.horoscapp.domain.model.HoroscopeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HoroscopeDetailViewModel @Inject constructor(
    private val GetPredictionUseCase: GetPredictionUseCase
): ViewModel(){

    private var _state = MutableStateFlow<HoroscopeDetailState>(HoroscopeDetailState.Loading)
    val state: StateFlow<HoroscopeDetailState> = _state

    lateinit var horoscope: HoroscopeModel

    fun getHoroscope(sign: HoroscopeModel) {

        horoscope = sign
        viewModelScope.launch {//hilo principal
            _state.value = HoroscopeDetailState.Loading

            val result = withContext(Dispatchers.IO){ //hilo sencundario
                GetPredictionUseCase(sign.name)
            }

            if(result != null){
                _state.value = HoroscopeDetailState.Success(result.horoscope , result.sign, horoscope)
            }else{
                _state.value = HoroscopeDetailState.Error("Ha ocurrido un error, intentelo mas tarde")
            }


        }
    }
}

