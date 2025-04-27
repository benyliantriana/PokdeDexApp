package id.suspendfun.feature_pokemon.datasource

import androidx.paging.PagingData
import id.suspendfun.feature_pokemon.data.ui.PokemonData
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    fun getPokemonList(): Flow<PagingData<PokemonData>>
}
