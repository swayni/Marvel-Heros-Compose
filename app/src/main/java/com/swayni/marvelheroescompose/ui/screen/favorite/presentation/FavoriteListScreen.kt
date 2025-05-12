package com.swayni.marvelheroescompose.ui.screen.favorite.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.swayni.marvelheroescompose.R
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import com.swayni.marvelheroescompose.ui.component.CollapsingToolbar
import com.swayni.marvelheroescompose.ui.component.DefaultListItem
import com.swayni.marvelheroescompose.ui.component.LoadingPopup
import com.swayni.marvelheroescompose.ui.screen.favorite.viewmodel.FavoriteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteListScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onNavigateToDetail: (character: CharacterModel) -> Unit
) {
    viewModel.getFavoriteCharacters()
    val successState by viewModel.successData.collectAsState()
    val loadingState by viewModel.loading.collectAsState()
    val errorState by viewModel.errorInfo.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CollapsingToolbar(scrollBehavior)
        }
    ) { innerPadding ->

        successState?.let { characters->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(colorResource(id = R.color.gray_custom_2)),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(characters, key = { it.id }){ character ->
                        val name = character.name
                        val imageUrl = character.imageUrl
                        DefaultListItem(name, imageUrl){
                            onNavigateToDetail.invoke(character)
                        }
                    }
                }
            }
        }

        if(loadingState) LoadingPopup()

        errorState?.let {
            Text(text = it.errorMessage)
        }
    }
}