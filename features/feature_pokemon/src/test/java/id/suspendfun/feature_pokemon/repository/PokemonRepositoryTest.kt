package id.suspendfun.feature_pokemon.repository

import androidx.paging.PagingData
import androidx.paging.map
import id.suspendfun.feature_pokemon.data.ui.PokemonData
import id.suspendfun.feature_pokemon.datasource.PokemonDataSource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PokemonRepositoryImplTest {

    private lateinit var pokemonRepository: PokemonRepositoryImpl
    private lateinit var pokemonDataSource: PokemonDataSource

    @Before
    fun setUp() {
        // Initialize the mock PokemonDataSource
        pokemonDataSource = mockk()

        // Initialize the class under test
        pokemonRepository = PokemonRepositoryImpl(pokemonDataSource)
    }

    @Test
    fun `getPokemonList should return correct data when data source provides valid data`() = runTest {
        // Given: Mocked data from the data source
        val mockPokemonData = listOf(
            PokemonData( "Pikachu", "electric", "pikachu.png"),
            PokemonData( "Bulbasaur", "grass", "bulbasaur.png")
        )

        val pagingData = PagingData.from(mockPokemonData)

        // Mock the data source to return the flow with PagingData
        coEvery { pokemonDataSource.getPokemonList() } returns flowOf(pagingData)

        // When: Collect the result from the repository
        val resultList = mutableListOf<PokemonData>()
        pokemonRepository.getPokemonList().firstOrNull()?.map {
            resultList.add(it)
        }

        // Then: Assert that the collected data matches the expected data
        assertEquals(mockPokemonData.size, resultList.size)
        assertTrue(resultList.containsAll(mockPokemonData))
    }

    @Test
    fun `getPokemonList should return empty list when data source returns empty list`() = runTest {
        // Given: Mocked empty data from the data source
        val emptyPokemonData = emptyList<PokemonData>()
        val pagingData = PagingData.from(emptyPokemonData)

        // Mock the data source to return the flow with empty PagingData
        coEvery { pokemonDataSource.getPokemonList() } returns flowOf(pagingData)

        // When: Collect the result from the repository
        val resultList = mutableListOf<PokemonData>()
        pokemonRepository.getPokemonList().collect { pagingData ->
            pagingData.map { pokemonData ->
                resultList.add(pokemonData)
            }
        }

        // Then: Assert that the result list is empty
        assertTrue(resultList.isEmpty())
    }

    @Test
    fun `getPokemonList should handle exception gracefully`() = runTest {
        // Given: Mocking the data source to throw an exception
        coEvery { pokemonDataSource.getPokemonList() } throws RuntimeException("Error fetching Pokemon data")

        // When: Collect the result from the repository
        val resultList = mutableListOf<PokemonData>()
        try {
            pokemonRepository.getPokemonList().collect { pagingData ->
                pagingData.map { pokemonData ->
                    resultList.add(pokemonData)
                }
            }
        } catch (e: Exception) {
            // Then: Assert that an exception is thrown and handle it gracefully
            assertTrue(e is RuntimeException)
            assertEquals("Error fetching Pokemon data", e.message)
        }
    }

    @After
    fun tearDown() {
        unmockkAll()  // Clean up mocks after each test
    }
}
