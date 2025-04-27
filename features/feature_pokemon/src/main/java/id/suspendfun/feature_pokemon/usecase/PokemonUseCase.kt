package id.suspendfun.feature_pokemon.usecase

import androidx.paging.PagingData
import id.suspendfun.feature_pokemon.data.ui.PokemonData
import kotlinx.coroutines.flow.Flow

/**
 * Not a fan of use case
 *
 * I think if the use case do nothing and directly return data from repository
 * better call repository direct from view model
 *
 * But use case is required if we combine data from multiple repositories
 * or we need to calculate something before we call function from repository
 */
interface PokemonUseCase {
    fun getPokemonList(): Flow<PagingData<PokemonData>>
}
