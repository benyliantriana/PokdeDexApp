package id.suspendfun.feature_pokemon.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.suspendfun.feature_pokemon.api.PokemonApi
import id.suspendfun.feature_pokemon.data.response.PokemonApiResponse
import id.suspendfun.feature_pokemon.data.response.PokemonResponse
import id.suspendfun.lib_room.dao.PokemonDao
import id.suspendfun.lib_room.dao.RemoteDao
import id.suspendfun.lib_room.db.PokemonDatabase
import id.suspendfun.lib_room.entity.PokemonEntity
import id.suspendfun.lib_room.entity.RemoteEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.awaitResponse
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
class PokemonMediatorTest {

    private lateinit var mediator: PokemonMediator
    private val pokemonApi: PokemonApi = mockk()
    private val database: PokemonDatabase = mockk()
    private val pokemonDao: PokemonDao = mockk()
    private val remoteDao: RemoteDao = mockk()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        every { database.pokemonDao() } returns pokemonDao
        every { database.remoteDao() } returns remoteDao
        mediator = PokemonMediator(pokemonApi, database)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refresh - valid cache - should return success without calling api`() = runTest {
        // Given
        val remoteEntity = RemoteEntity(
            id = "pokemon",
            lastUpdated = System.currentTimeMillis()
        )

        coEvery { remoteDao.getRemoteEntity("pokemon") } returns remoteEntity

        val pagingState = PagingState<Int, PokemonEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        // When
        val result = mediator.load(LoadType.REFRESH, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        coVerify(exactly = 0) { pokemonApi.getPokemonList(any()) }
    }

    @Test
    fun `refresh - expired cache - should fetch from api and save to db`() = runTest {
        // Given
        val oldRemoteEntity = RemoteEntity(
            id = "pokemon",
            lastUpdated = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(10)
        )

        val pokemonResponse = PokemonApiResponse(
            results = listOf(
                PokemonResponse(name = "bulbasaur", url = "url/bulbasaur")
            )
        )

        val response = mockk<retrofit2.Response<PokemonApiResponse>> {
            every { isSuccessful } returns true
            every { body() } returns pokemonResponse
        }

        coEvery { remoteDao.getRemoteEntity("pokemon") } returns oldRemoteEntity
        coEvery { pokemonApi.getPokemonList(offset = 0).awaitResponse() } returns response
        coEvery { database.withTransaction {} } coAnswers {
            val block = firstArg<suspend () -> Unit>()
            block()
        }
        coEvery { pokemonDao.deleteAllPokemon() } just Runs
        coEvery { pokemonDao.insertPokemon(any()) } just Runs
        coEvery { remoteDao.clearRemoteEntity() } just Runs
        coEvery { remoteDao.insertOrReplace(any()) } just Runs

        val pagingState = PagingState<Int, PokemonEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        // When
        val result = mediator.load(LoadType.REFRESH, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        coVerify(exactly = 1) { pokemonApi.getPokemonList(0).awaitResponse() }
        coVerify(exactly = 1) { pokemonDao.deleteAllPokemon() }
        coVerify(exactly = 1) { pokemonDao.insertPokemon(any()) }
    }

    @Test
    fun `prepend - should return success endOfPaginationReached true`() = runTest {
        // Given
        val pagingState = PagingState<Int, PokemonEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        // When
        val result = mediator.load(LoadType.PREPEND, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        coVerify(exactly = 1) { pokemonApi.getPokemonList(0).awaitResponse() }

        val success = result as RemoteMediator.MediatorResult.Success
        assertTrue(success.endOfPaginationReached)
    }

    @Test
    fun `append - successful response with data`() = runTest {
        // Given
        val pokemonResponse = PokemonApiResponse(
            results = listOf(
                PokemonResponse(name = "bulbasaur", url = "url/bulbasaur")
            )
        )

        val response = mockk<retrofit2.Response<PokemonApiResponse>> {
            every { isSuccessful } returns true
            every { body() } returns pokemonResponse
        }

        coEvery { pokemonApi.getPokemonList(any()).awaitResponse() } returns response
        coEvery { database.withTransaction {} } coAnswers {
            val block = firstArg<suspend () -> Unit>()
            block()
        }
        coEvery { pokemonDao.insertPokemon(any()) } just Runs

        val pagingState = PagingState<Int, PokemonEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        // When
        val result = mediator.load(LoadType.APPEND, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        coVerify(exactly = 1) { pokemonApi.getPokemonList(any()).awaitResponse() }
    }

    @Test
    fun `error from api - should return MediatorResult_Error`() = runTest {
        // Given
        coEvery {
            pokemonApi.getPokemonList(any()).awaitResponse()
        } throws RuntimeException("network error")

        val pagingState = PagingState<Int, PokemonEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        // When
        val result = mediator.load(LoadType.REFRESH, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}
