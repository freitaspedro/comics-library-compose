package com.example.comicslibrary

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.comicslibrary.ui.theme.ComicsLibraryTheme
import com.example.comicslibrary.view.*
import com.example.comicslibrary.viewmodel.LibraryViewModel
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String) {
    object Library: Destination("library")
    object Collection: Destination("collection")
    object CharacterDetails: Destination("character/{characterId}") {
        fun createRoute(characterId: Int?) = "character/$characterId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val libraryViewModel by viewModels<LibraryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicsLibraryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    CharactersScaffold(navController = navController, viewModel = libraryViewModel)
                }
            }
        }
    }
}

@Composable
fun CharactersScaffold(navController: NavHostController, viewModel: LibraryViewModel) {
    val scaffoldState = rememberScaffoldState()

    Scaffold (
        scaffoldState = scaffoldState,
        bottomBar = { ComicsBottomNav(navController = navController) }
    ) {
        paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Destination.Library.route
            ) {
                composable(Destination.Library.route) {
                    LibraryScreen(navController, viewModel, paddingValues)
                }
                composable(Destination.Collection.route) {
                    CollectionScreen()
                }
                composable(Destination.CharacterDetails.route) { navBackStackEntry ->
                    val id = navBackStackEntry.arguments?.getString("characterId")?.toIntOrNull()
                    if (id == null) {
                        Toast.makeText(
                             LocalContext.current,
                             "Character id is required",
                             Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.retrieveCharacter(id)
                        CharacterDetailsScreen(
                            navController = navController,
                            viewModel = viewModel,
                            paddingValues = paddingValues
                        )
                    }
                }
            }
    }


}