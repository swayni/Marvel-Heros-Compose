package com.swayni.marvelheroescompose


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.swayni.marvelheroescompose.core.navigation.BottomNavigationItemModel
import com.swayni.marvelheroescompose.core.navigation.NavigationKeys.Route.DETAIL
import com.swayni.marvelheroescompose.core.navigation.NavigationKeys.Route.FAVORITE
import com.swayni.marvelheroescompose.core.navigation.NavigationKeys.Route.LIST
import com.swayni.marvelheroescompose.domain.model.CharacterModel
import com.swayni.marvelheroescompose.ui.screen.detail.presentation.DetailScreen
import com.swayni.marvelheroescompose.ui.screen.favorite.presentation.FavoriteListScreen
import com.swayni.marvelheroescompose.ui.screen.list.presentation.ListScreen
import com.swayni.marvelheroescompose.ui.theme.MarvelHerosTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            MarvelHerosTheme {
                MarvelHerosApp()
            }
        }
    }
}

@Preview(showBackground = true)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MarvelHerosApp() {
    val navController = rememberNavController()

    var isBottomExpanded by remember { mutableStateOf(true) }
    var characterModel by remember { mutableStateOf<CharacterModel?>(null) }

    Scaffold(
        bottomBar = { if (isBottomExpanded) {BottomNavigationView(navController)} },
        content = {
            NavHost(navController = navController, startDestination = "list") {
                composable(LIST) {
                    isBottomExpanded = true
                    ListScreen(
                        onNavigateToDetail = { character ->
                            characterModel = CharacterModel(
                                id = character.id,
                                name = character.name,
                                description = character.description,
                                imageUrl = "${character.thumbnail.path.replace("http://","https://")}/standard_amazing.${character.thumbnail.extension}"
                            )
                            navController.navigate(route = "detail")
                        }
                    )
                }
                composable(FAVORITE) {
                    isBottomExpanded = true
                    FavoriteListScreen(
                        onNavigateToDetail = { character ->
                            characterModel = character
                            navController.navigate(route = "detail")
                        }
                    )
                }
                composable(
                    route = DETAIL
                ) {
                    isBottomExpanded = false
                    DetailScreen(characterModel){
                        navController.popBackStack()
                    }
                }
            }
        }
    )
}


@Composable
fun BottomNavigationView(navController: NavController){
    val items = listOf(
        BottomNavigationItemModel(
            title = "List",
            icon = Icons.AutoMirrored.Filled.List,
            route = LIST
        ),
        BottomNavigationItemModel(
            title = "Favorite",
            icon = Icons.Default.Favorite,
            route = FAVORITE
        )
    )

    BottomNavigation (
        backgroundColor = colorResource(R.color.gray_custom_1),
        contentColor = colorResource(R.color.gray_custom_1)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title, tint = if (currentRoute == item.route) { colorResource(R.color.red) } else { colorResource(R.color.white) })},
                label = { Text(text = item.title, color = if (currentRoute == item.route) { colorResource(R.color.red) } else { colorResource(R.color.white) }, fontSize = 9.sp) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = true,
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f)
            )
        }
    }
}