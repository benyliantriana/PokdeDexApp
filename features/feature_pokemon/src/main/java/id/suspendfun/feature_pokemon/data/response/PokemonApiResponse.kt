package id.suspendfun.feature_pokemon.data.response

import kotlinx.serialization.Serializable

@Serializable
data class PokemonApiResponse(
    val count: Long = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonResponse> = emptyList()
)
