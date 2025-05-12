package com.swayni.marvelheroescompose.core.navigation


sealed class NavigationKeys {
    object Route {
        const val LIST = "list"
        const val FAVORITE = "favorite"
        const val DETAIL = "detail"
    }
}