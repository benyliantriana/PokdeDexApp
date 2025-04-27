package id.suspendfun.feature_pokemon.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.suspendfun.feature_pokemon.api.PokemonApi
import id.suspendfun.lib_room.db.PokemonDatabase
import id.suspendfun.lib_room.entity.PokemonEntity
import id.suspendfun.lib_room.entity.RemoteEntity
import retrofit2.awaitResponse
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PokemonMediator(
    private val pokemonApi: PokemonApi,
    private val database: PokemonDatabase
) : RemoteMediator<Int, PokemonEntity>() {

    private val cacheTimeout = TimeUnit.MINUTES.toMillis(5)
    private val remoteEntityId = "pokemon"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        val pokemonDao = database.pokemonDao()
        val remoteDao = database.remoteDao()

        if (loadType == LoadType.REFRESH) {
            val remoteKeys = remoteDao.getRemoteEntity(remoteEntityId)
            val currentTime = System.currentTimeMillis()
            if (remoteKeys != null) {
                val isCacheValid = (currentTime - remoteKeys.lastUpdated) < cacheTimeout
                if (isCacheValid) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
            }
        }

        val offset = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val itemCount = state.pages.sumOf { it.data.size }.toLong()
                itemCount
            }
        }

        return try {
            val response = pokemonApi.getPokemonList(
                offset = offset
            ).awaitResponse()
            if (response.isSuccessful) {
                val pokemonList = response.body()?.results?.map { it.toPokemonEntity() }
                val endOfPaginationReached = pokemonList?.isEmpty() == true

                pokemonList?.let {
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            pokemonDao.deleteAllPokemon()
                            remoteDao.clearRemoteEntity()

                            remoteDao.insertOrReplace(
                                RemoteEntity(
                                    id = remoteEntityId,
                                    lastUpdated = System.currentTimeMillis()
                                )
                            )
                        }
                        pokemonDao.insertPokemon(pokemonList)
                    }
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                MediatorResult.Error(Throwable(message = response.message()))
            }

        } catch (e: Exception) {
            if (e.message.isNullOrBlank()) {
                MediatorResult.Error(Throwable(message = "Failed to fetching data"))
            } else {
                MediatorResult.Error(e)
            }
        }
    }
}
