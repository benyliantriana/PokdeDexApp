package id.suspendfun.feature_pokemon.ui

import id.suspendfun.feature_pokemon.data.ui.PokemonData

sealed class PokemonUiState {
    data object Loading : PokemonUiState()
    data class Success(val pokemonData: List<PokemonData>) : PokemonUiState()
    data class Failed(val code: Int, val message: String) : PokemonUiState()
}
