package id.suspendfun.feature_pokemon.api

import id.suspendfun.feature_pokemon.data.response.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Long = 0,
    ): Call<PokemonResponse>
}
