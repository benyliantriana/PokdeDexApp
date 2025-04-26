package id.suspendfun.feature_pokemon.datasource

import id.suspendfun.feature_pokemon.api.PokemonApi
import id.suspendfun.feature_pokemon.data.response.PokemonResponse
import id.suspendfun.lib_base.di.IODispatcher
import id.suspendfun.lib_base.exception.getDefaultRemoteException
import id.suspendfun.lib_network.response.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject

class PokemonDataSourceImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : PokemonDataSource {
    override suspend fun getPokemonList(offset: Long): BaseResponse<PokemonResponse> =
        withContext(ioDispatcher) {
            val defaultExceptionData = getDefaultRemoteException()
            var responseDefault: BaseResponse<PokemonResponse> = BaseResponse.Failed(
                code = defaultExceptionData.code, message = defaultExceptionData.message
            )

            val result = pokemonApi.getPokemonList(
                offset = offset
            ).awaitResponse()

            if (result.isSuccessful) {
                result.body()?.let {
                    responseDefault = BaseResponse.Success(
                        PokemonResponse(
                            count = it.count,
                            next = it.next,
                            previous = it.previous,
                            results = it.results
                        )
                    )
                }
                if (result.body()?.results.isNullOrEmpty()) {
                    responseDefault = BaseResponse.Failed(result.code(), "Pokemon list not found!")
                }
            } else {
                responseDefault = BaseResponse.Failed(result.code(), result.message())
            }
            return@withContext responseDefault
        }
}
