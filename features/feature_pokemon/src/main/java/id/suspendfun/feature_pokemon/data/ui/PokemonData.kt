package id.suspendfun.feature_pokemon.data.ui

import kotlinx.serialization.Serializable

@Serializable
data class PokemonData(
    val name: String,
    val url: String,
)
