package id.suspendfun.feature_pokemon.repository

import id.suspendfun.feature_pokemon.data.ui.PokemonData
import id.suspendfun.feature_pokemon.datasource.PokemonDataSource
import id.suspendfun.lib_network.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDataSource: PokemonDataSource,
) : PokemonRepository {
    override suspend fun getPokemonList(offset: Long): Flow<BaseResponse<List<PokemonData>>> =
        flow {
            emit(BaseResponse.Loading)
            val pokemonResponse = pokemonDataSource.getPokemonList(offset)
            if (pokemonResponse is BaseResponse.Success) {
                emit(BaseResponse.Success(pokemonResponse.data.results))
            } else if (pokemonResponse is BaseResponse.Failed) {
                emit(BaseResponse.Failed(pokemonResponse.code, pokemonResponse.message))
            }
        }
}
