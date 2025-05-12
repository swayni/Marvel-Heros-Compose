package com.swayni.marvelheroescompose.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItemModel(
    val title: String,
    val icon: ImageVector,
    val route: String
)
