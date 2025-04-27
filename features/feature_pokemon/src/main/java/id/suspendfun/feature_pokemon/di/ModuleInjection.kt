package id.suspendfun.feature_pokemon.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.suspendfun.feature_pokemon.api.PokemonApi
import id.suspendfun.feature_pokemon.datasource.PokemonDataSource
import id.suspendfun.feature_pokemon.datasource.PokemonDataSourceImpl
import id.suspendfun.feature_pokemon.repository.PokemonRepository
import id.suspendfun.feature_pokemon.repository.PokemonRepositoryImpl
import id.suspendfun.feature_pokemon.usecase.PokemonUseCase
import id.suspendfun.feature_pokemon.usecase.PokemonUseCaseImpl
import id.suspendfun.lib_network.service.ApiService
import id.suspendfun.lib_room.db.PokemonDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleInjection {
    @Provides
    @Singleton
    fun providePokemonUseCase(
        pokemonRepository: PokemonRepository,
    ): PokemonUseCase =
        PokemonUseCaseImpl(
            pokemonRepository,
        )

    @Provides
    @Singleton
    fun providePokemonRepository(
        pokemonDataSource: PokemonDataSource
    ): PokemonRepository =
        PokemonRepositoryImpl(
            pokemonDataSource,
        )

    @Provides
    @Singleton
    fun providePokemonDataSource(
        pokemonApi: PokemonApi,
        pokemonDatabase: PokemonDatabase,
    ): PokemonDataSource =
        PokemonDataSourceImpl(
            pokemonApi,
            pokemonDatabase
        )

    @Provides
    @Singleton
    fun providePokemonApi(
        apiService: ApiService,
    ): PokemonApi = apiService.service().create(PokemonApi::class.java)
}