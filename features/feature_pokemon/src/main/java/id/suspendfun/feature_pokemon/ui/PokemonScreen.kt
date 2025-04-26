package id.suspendfun.feature_pokemon.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import id.suspendfun.feature_pokemon.data.ui.PokemonData
import id.suspendfun.feature_pokemon.util.PokemonBaseImageUrl
import id.suspendfun.lib_ui.R as RUi

@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel = hiltViewModel<PokemonViewModel>(),
) {
    val pokemonUiState =
        viewModel.pokemonUiState.collectAsStateWithLifecycle(PokemonUiState.Loading).value
    val gridState = rememberLazyGridState()
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = gridState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == gridState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) viewModel.getPokemonList()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) { innerPadding ->
        when (pokemonUiState) {
            is PokemonUiState.Success -> {
                LazyVerticalGrid(
                    state = gridState,
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                ) {
                    itemsIndexed(pokemonUiState.pokemonData) { _, pokemon ->
                        PokemonItemView(pokemon)
                    }
                }
            }

            is PokemonUiState.Failed -> Text(pokemonUiState.message)
            PokemonUiState.Loading -> Text(stringResource(RUi.string.loading))
        }
    }
}

@Composable
fun PokemonItemView(pokemon: PokemonData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = "$PokemonBaseImageUrl${pokemon.name}.jpg",
            contentDescription = null,
        )
        Spacer(Modifier.height(4.dp))
        Text(pokemon.name)
    }
}