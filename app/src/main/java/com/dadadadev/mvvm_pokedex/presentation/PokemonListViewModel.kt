package com.dadadadev.mvvm_pokedex.presentation

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.dadadadev.mvvm_pokedex.domain.PokemonRepository
import com.dadadadev.mvvm_pokedex.domain.models.PokedexListEntry
import com.dadadadev.mvvm_pokedex.util.Constants
import com.dadadadev.mvvm_pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    private var currentPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
        private set
    var loadError = mutableStateOf("")
        private set
    var isLoading = mutableStateOf(false)
        private set
    var endReached= mutableStateOf(false)
        private set

    init {
        loadPokemonPaginated()
    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokedexListEntries(Constants.PAGE_SIZE, currentPage * Constants.PAGE_SIZE)
            when (result) {
                is Resource.Success -> {
                    endReached.value = currentPage * Constants.PAGE_SIZE >= result.data!!.size
                    currentPage++

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value = result.data
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                else -> Unit
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        val dominantColor = Palette.from(bmp).generate().getDominantColor(Color.White.toArgb())
        onFinish(Color(dominantColor))
    }
}