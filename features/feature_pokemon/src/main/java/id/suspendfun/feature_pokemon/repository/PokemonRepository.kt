package id.suspendfun.feature_pokemon.repository

import id.suspendfun.feature_pokemon.data.ui.PokemonData
import id.suspendfun.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonList(offset: Long): Flow<BaseResponse<List<PokemonData>>>
}