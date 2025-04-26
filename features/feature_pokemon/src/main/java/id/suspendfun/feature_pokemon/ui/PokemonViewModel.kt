package id.suspendfun.feature_pokemon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.suspendfun.feature_pokemon.repository.PokemonRepository
import id.suspendfun.lib_base.di.IODispatcher
import id.suspendfun.lib_network.response.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PokemonViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private var _pokemonUiState = MutableStateFlow<PokemonUiState>(PokemonUiState.Loading)
    val pokemonUiState: StateFlow<PokemonUiState> get() = _pokemonUiState

    private var index = FIRST_INDEX

    init {
        getPokemonList()
    }

    fun getPokemonList() {
        viewModelScope.launch(ioDispatcher) {
            val offset = DEFAULT_OFFSET * index
            pokemonRepository.getPokemonList(offset).collect { result ->
                when (result) {
                    is BaseResponse.Loading -> {
                        if (index == FIRST_INDEX) {
                            _pokemonUiState.value = PokemonUiState.Loading
                        }
                    }

                    is BaseResponse.Success -> {
                        val currentState = _pokemonUiState.value
                        if (currentState is PokemonUiState.Success) {
                            val updatedList = currentState.pokemonData + result.data
                            _pokemonUiState.value = PokemonUiState.Success(updatedList)
                        } else {
                            _pokemonUiState.value = PokemonUiState.Success(result.data)
                        }
                    }

                    is BaseResponse.Failed -> {
                        _pokemonUiState.value = PokemonUiState.Failed(result.code, result.message)
                    }
                }
            }
            index++
        }
    }

    companion object {
        const val DEFAULT_OFFSET = 20
        const val FIRST_INDEX = 0L
    }
}
