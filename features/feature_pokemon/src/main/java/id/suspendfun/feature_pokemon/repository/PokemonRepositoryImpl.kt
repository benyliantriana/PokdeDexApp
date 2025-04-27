package id.suspendfun.feature_pokemon.repository

import androidx.paging.PagingData
import id.suspendfun.feature_pokemon.data.ui.PokemonData
import id.suspendfun.feature_pokemon.datasource.PokemonDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDataSource: PokemonDataSource,
) : PokemonRepository {
    override fun getPokemonList(): Flow<PagingData<PokemonData>> =
        pokemonDataSource.getPokemonList()
}
