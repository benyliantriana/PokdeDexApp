package id.suspendfun.feature_pokemon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.suspendfun.feature_pokemon.usecase.PokemonUseCase
import id.suspendfun.lib_base.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
open class PokemonViewModel @Inject constructor(
    pokemonUseCase: PokemonUseCase,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    val pokemonPagingFlow = pokemonUseCase.getPokemonList()
        .flowOn(ioDispatcher)
        .cachedIn(viewModelScope)
}
