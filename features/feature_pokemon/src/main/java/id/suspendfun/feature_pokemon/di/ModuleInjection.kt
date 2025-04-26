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
import id.suspendfun.lib_base.di.IODispatcher
import id.suspendfun.lib_network.service.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleInjection {
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
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): PokemonDataSource =
        PokemonDataSourceImpl(
            pokemonApi,
            ioDispatcher
        )

    @Provides
    @Singleton
    fun providePokemonApi(
        apiService: ApiService,
    ): PokemonApi = apiService.service().create(PokemonApi::class.java)
}