package com.swayni.marvelheroescompose.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.swayni.marvelheroescompose.R

@Composable
fun ButtonIconFavorite(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onFavoriteClick,
        modifier = modifier,
    ) {
        Icon(
            imageVector = if (isFavorite) {
                Icons.Default.Favorite} else {
                Icons.Default.FavoriteBorder},
            contentDescription = stringResource(R.string.favorite),
            tint = colorResource(R.color.red)
        )
    }
}