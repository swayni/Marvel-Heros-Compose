package com.swayni.marvelheroescompose.ui.screen.detail.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.swayni.marvelheroescompose.R
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import com.swayni.marvelheroescompose.ui.component.ButtonIconFavorite
import com.swayni.marvelheroescompose.ui.component.DefaultListItem
import com.swayni.marvelheroescompose.ui.component.LoadingPopup
import com.swayni.marvelheroescompose.ui.screen.detail.viewmodel.DetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    character: CharacterModel?,
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
){
    if (character == null) return
    viewModel.getComics(character.id)
    viewModel.checkFavorite(character)

    val successData by viewModel.successData.collectAsState()
    val errorData by viewModel.errorInfo.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val uiIsFavoriteState by viewModel.uiIsFavoriteState.collectAsState()


    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.detail),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.gray_custom_1)
                ),
                actions = {
                    IconButton(
                        onClick = { }
                    ) {}
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = stringResource(R.string.back),
                            tint = Color.White
                        )
                    }
                }
                )
        }
    ) {  innerPadding ->
        successData?.let { data ->
            val imageUrl = character.imageUrl
            Column (modifier = Modifier.fillMaxWidth().fillMaxHeight(1f).padding(innerPadding).background(colorResource(R.color.gray_custom_2))) {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    rememberImagePainter(imageUrl),
                    contentDescription = stringResource(R.string.character_image),
                    modifier = Modifier
                        .wrapContentSize()
                        .height(250.dp)
                        .width(250.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(20))
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.red),
                            shape = RoundedCornerShape(20)
                        ),
                )

                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(character.name,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 23.dp)
                            .weight(1f),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    )

                    ButtonIconFavorite(
                        uiIsFavoriteState,
                        onFavoriteClick = {
                            if (uiIsFavoriteState) { viewModel.removeFavorite(character) } else { viewModel.addFavorite(character) }
                                          },
                        modifier = Modifier
                            .background(colorResource(id = android.R.color.transparent), shape = RoundedCornerShape(15.dp))
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.description),
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = character.description,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (data.isNotEmpty()){
                    Text(
                        text = stringResource(R.string.comics),
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold)
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = innerPadding
                    ){
                        items(data, key = { it.id }) { comic ->
                            DefaultListItem(
                                name = comic.title,
                                imageUrl = "${comic.thumbnail.path.replace("http://","https://")}/standard_amazing.${comic.thumbnail.extension}",
                                onClick = {}
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        if(isLoading) LoadingPopup()

        errorData?.let {
            Text(text = it.errorMessage)
        }
    }
}


