package id.suspendfun.feature_pokemon.data.response

import id.suspendfun.lib_room.entity.PokemonEntity
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    val name: String,
    val url: String,
) {
    fun toPokemonEntity(): PokemonEntity = PokemonEntity(
        id = 0,
        name = this.name,
        url = this.url,
    )
}
