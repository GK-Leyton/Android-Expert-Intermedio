package com.heiderleyton.horoscapp.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.heiderleyton.horoscapp.R
import com.heiderleyton.horoscapp.databinding.ActivityHoroscopeDetailBinding
import com.heiderleyton.horoscapp.domain.model.HoroscopeModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HoroscopeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHoroscopeDetailBinding
    private val horoscopeDetailViewModel: HoroscopeDetailViewModel by viewModels()

    private val args:HoroscopeDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHoroscopeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        initUi()
        horoscopeDetailViewModel.getHoroscope(args.type)

    }

    private fun initUi() {
        initListener()
        initUIState()
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener{
            onBackPressed()
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                horoscopeDetailViewModel.state.collect{
                    when(it){
                        HoroscopeDetailState.Loading -> loadingState()
                        is HoroscopeDetailState.Error -> errorState()
                        is HoroscopeDetailState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.isVisible = true

    }

    private fun errorState(){
        binding.progressBar.isVisible = false

    }

    private fun successState(state: HoroscopeDetailState.Success) {
        binding.progressBar.isVisible = false
        binding.tvTitle.text = state.sign
        binding.tvBody.text = state.data

        val image = when(state.horoscopeModel){
            HoroscopeModel.Aries -> R.drawable.detail_aries
            HoroscopeModel.Taurus -> R.drawable.detail_taurus
            HoroscopeModel.Gemini -> R.drawable.detail_gemini
            HoroscopeModel.Cancer -> R.drawable.detail_cancer
            HoroscopeModel.Leo -> R.drawable.detail_leo
            HoroscopeModel.Virgo -> R.drawable.detail_virgo
            HoroscopeModel.Libra -> R.drawable.detail_libra
            HoroscopeModel.Scorpio -> R.drawable.detail_scorpio
            HoroscopeModel.Sagittarius -> R.drawable.detail_sagittarius
            HoroscopeModel.Capricorn -> R.drawable.detail_capricorn
            HoroscopeModel.Aquarius -> R.drawable.detail_aquarius
            HoroscopeModel.Pisces -> R.drawable.detail_pisces
        }

        binding.ivDetail.setImageResource(image)
    }
}