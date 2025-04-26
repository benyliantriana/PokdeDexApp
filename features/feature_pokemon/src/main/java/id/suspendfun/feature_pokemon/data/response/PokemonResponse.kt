package id.suspendfun.feature_pokemon.data.response

import id.suspendfun.feature_pokemon.data.ui.PokemonData
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    val count: Long = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonData> = emptyList()
)
