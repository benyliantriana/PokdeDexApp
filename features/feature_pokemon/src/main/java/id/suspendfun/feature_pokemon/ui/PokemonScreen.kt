package id.suspendfun.feature_pokemon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import id.suspendfun.feature_pokemon.data.ui.PokemonData
import id.suspendfun.lib_ui.R as RUi

@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel = hiltViewModel<PokemonViewModel>(),
) {
    val pokemonUiState = viewModel.pokemonPagingFlow.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) { innerPadding ->
        when (val state = pokemonUiState.loadState.refresh) {
            is LoadState.Loading -> {
                Text(stringResource(RUi.string.loading))
            }

            is LoadState.Error -> {
                Text(state.error.message.toString())
            }

            is LoadState.NotLoading -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(
                        top = innerPadding.calculateTopPadding(),
                        start = 8.dp,
                        end = 8.dp
                    ),
                ) {
                    items(pokemonUiState.itemCount) { index ->
                        pokemonUiState[index]?.let {
                            PokemonItemView(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonItemView(pokemon: PokemonData) {
    Card(
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color = colorResource(RUi.color.white))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imageUrl)
                    .size(250)
                    .build(),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = pokemon.name
            )
        }
    }
}