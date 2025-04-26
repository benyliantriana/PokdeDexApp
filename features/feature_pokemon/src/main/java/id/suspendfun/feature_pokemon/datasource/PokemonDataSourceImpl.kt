package id.suspendfun.feature_pokemon.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import id.suspendfun.feature_pokemon.api.PokemonApi
import id.suspendfun.feature_pokemon.data.ui.PokemonData
import id.suspendfun.feature_pokemon.mediator.PokemonMediator
import id.suspendfun.feature_pokemon.util.toPokemonData
import id.suspendfun.lib_room.db.PokemonDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonDataSourceImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase,
) : PokemonDataSource {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemonList(): Flow<PagingData<PokemonData>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PokemonMediator(pokemonApi, pokemonDatabase),
            pagingSourceFactory = { pokemonDatabase.pokemonDao().getAllPokemon() }
        ).flow
            .map { entities ->
                entities.map { entity ->
                    entity.toPokemonData()
                }
            }
}
