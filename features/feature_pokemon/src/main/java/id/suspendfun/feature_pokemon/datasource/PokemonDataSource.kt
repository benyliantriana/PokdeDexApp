package id.suspendfun.feature_pokemon.datasource

import id.suspendfun.feature_pokemon.data.response.PokemonResponse
import id.suspendfun.lib_network.response.BaseResponse

interface PokemonDataSource {
    suspend fun getPokemonList(offset: Long): BaseResponse<PokemonResponse>
}
