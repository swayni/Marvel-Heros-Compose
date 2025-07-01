@file:OptIn(ExperimentalMaterial3Api::class)

package com.swayni.marvelheroescompose.ui.screen.list.presentation


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.swayni.marvelheroescompose.data.model.Character
import com.swayni.marvelheroescompose.ui.component.CollapsingToolbar
import com.swayni.marvelheroescompose.ui.component.DefaultListItem
import com.swayni.marvelheroescompose.ui.component.LoadingPopup
import com.swayni.marvelheroescompose.ui.screen.list.viewmodel.ListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "Range")
@Composable
fun ListScreen(
    viewModel: ListViewModel = hiltViewModel(),
    onNavigateToDetail: (Character) -> Unit = {}
){
    viewModel.getCharacters()
    val successState by viewModel.successData.collectAsState()
    val loadingState by viewModel.loading.collectAsState()
    val errorState by viewModel.errorInfo.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CollapsingToolbar(scrollBehavior, scrollBehavior.state.collapsedFraction > 0.25f)
        }
    ) { innerPadding ->

        successState?.let { characters->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(colorResource(id = R.color.gray_custom_2)),
            ) {
                LazyColumn(
                    modifier = Modifier,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(characters, key = { it.id }){ character ->
                        val name = character.name
                        val imageUrl = "${character.thumbnail.path.replace("http://","https://")}/standard_amazing.${character.thumbnail.extension}"
                        DefaultListItem(name, imageUrl){
                            onNavigateToDetail.invoke(character)
                        }
                        viewModel.loadMoreCharacters(character)
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

