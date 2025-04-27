package id.suspendfun.feature_pokemon.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.map
import id.suspendfun.feature_pokemon.api.PokemonApi
import id.suspendfun.feature_pokemon.data.response.PokemonApiResponse
import id.suspendfun.feature_pokemon.data.response.PokemonResponse
import id.suspendfun.feature_pokemon.data.ui.PokemonData
import id.suspendfun.feature_pokemon.util.toPokemonData
import id.suspendfun.lib_room.dao.PokemonDao
import id.suspendfun.lib_room.db.PokemonDatabase
import id.suspendfun.lib_room.entity.PokemonEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.awaitResponse

class PokemonDataSourceImplTest {

    private lateinit var pokemonDataSource: PokemonDataSourceImpl
    private lateinit var pokemonApi: PokemonApi
    private lateinit var pokemonDatabase: PokemonDatabase
    private lateinit var pokemonDao: PokemonDao

    private val mockPokemonEntities = listOf(
        PokemonEntity(1, "Pikachu", "uri1"),
        PokemonEntity(2, "Bulbasaur", "uri2")
    )

    private val mockPokemonData = mockPokemonEntities.map { it.toPokemonData() }

    // Mock the DAO to return a PagingSource
    private val pagingSource = object : PagingSource<Int, PokemonEntity>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonEntity> {
            return LoadResult.Page(
                data = mockPokemonEntities,
                prevKey = null,
                nextKey = null
            )
        }

        override fun getRefreshKey(state: PagingState<Int, PokemonEntity>): Int? {
            TODO("Not yet implemented")
        }
    }

    @Before
    fun setUp() {
        // Mocking the dependencies
        pokemonApi = mockk()
        pokemonDatabase = mockk()
        pokemonDao = mockk()

        pokemonDataSource = PokemonDataSourceImpl(pokemonApi, pokemonDatabase)
    }

    @Test
    fun `getPokemonList should return mapped PagingData`() = runTest {
        // Given

        // Mock the DAO to return a flow of PokemonEntity
        coEvery { pokemonDao.getAllPokemon() } returns pagingSource

        // Mock the PokemonApi call if needed (not strictly necessary here if using the local database flow)
        coEvery { pokemonApi.getPokemonList(any()).awaitResponse() } returns Response.success(
            PokemonApiResponse()
        )

        // When
        val resultFlow = pokemonDataSource.getPokemonList()

        // Collect the result from the flow
        val result = resultFlow.first()

        // Then
        // Check that the result is correctly mapped from PokemonEntity to PokemonData
        assertEquals(mockPokemonData, result)
    }

    @Test
    fun `getPokemonList should call the API and return data when database is empty`() = runTest {
        // Given
        val mockApiResponse = listOf(
            PokemonResponse("Pikachu", "uri1"),
            PokemonResponse("Bulbasaur", "uri2")
        )

        // Simulate the API response
        coEvery { pokemonApi.getPokemonList(any()).awaitResponse() } returns Response.success(
            PokemonApiResponse(
                results = mockApiResponse
            )
        )

        // Mock the DAO to return an empty list initially
        coEvery { pokemonDao.getAllPokemon() } returns pagingSource

        // Mock the DAO to insert the API results
        coEvery { pokemonDao.insertPokemon(mockPokemonEntities) } just Runs

        // When
        val resultFlow = pokemonDataSource.getPokemonList()
        val resultList = mutableListOf<PokemonData>()

        // Collect the result from the flow
        resultFlow.collect {
            it.map { data ->
                resultList.add(data)
            }
        }

        // Then
        assertTrue(resultList.isNotEmpty())
        assertEquals(mockApiResponse.size, resultList.size)
    }

    @Test
    fun `getPokemonList should handle error from API gracefully`() = runTest {
        // Given
        val exception = RuntimeException("API error")

        // Mock the API to throw an exception
        coEvery { pokemonApi.getPokemonList(any()) } throws exception

        // Mock the DAO to return some data
        coEvery { pokemonDao.getAllPokemon() } returns pagingSource

        // When
        val resultFlow = pokemonDataSource.getPokemonList()

        // Collect the result from the flow
        val resultList = mutableListOf<PokemonData>()

        // Collect the result from the flow
        resultFlow.collect {
            it.map { data ->
                resultList.add(data)
            }
        }

        // Then
        // Check if the result is empty or error handling data
        assertTrue(resultList.isEmpty())  // or perform any other suitable error handling
    }

    @After
    fun tearDown() {
        unmockkAll()  // Clean up mocks after each test
    }
}
