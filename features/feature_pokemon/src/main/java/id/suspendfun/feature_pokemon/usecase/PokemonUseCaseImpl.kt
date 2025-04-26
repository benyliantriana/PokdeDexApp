package id.suspendfun.feature_pokemon.usecase

import androidx.paging.PagingData
import id.suspendfun.feature_pokemon.data.ui.PokemonData
import id.suspendfun.feature_pokemon.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonUseCaseImpl(
    private val pokemonRepository: PokemonRepository
) : PokemonUseCase {
    override fun getPokemonList(): Flow<PagingData<PokemonData>> = pokemonRepository.getPokemonList()
}
