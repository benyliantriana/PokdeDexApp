package id.suspendfun.feature_pokemon.data.ui

import id.suspendfun.lib_room.entity.PokemonEntity
import kotlinx.serialization.Serializable

@Serializable
data class PokemonData(
    val name: String,
    val url: String,
    val imageUrl: String,
)
