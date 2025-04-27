package id.suspendfun.feature_pokemon.repository

import androidx.paging.PagingData
import id.suspendfun.feature_pokemon.data.ui.PokemonData
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(): Flow<PagingData<PokemonData>>
}