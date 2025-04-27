package id.suspendfun.feature_pokemon.util

import id.suspendfun.feature_pokemon.data.ui.PokemonData
import id.suspendfun.lib_room.entity.PokemonEntity

private const val PokemonBaseImageUrl = "https://img.pokemondb.net/artwork/large/"

fun PokemonEntity.toPokemonData(): PokemonData {
    return PokemonData(
        name = this.name,
        url = this.url,
        imageUrl = PokemonBaseImageUrl + this.name + ".jpg"
    )
}
