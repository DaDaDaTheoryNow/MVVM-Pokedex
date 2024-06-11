package com.dadadadev.mvvm_pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dadadadev.mvvm_pokedex.presentation.PokemonListScreen
import com.dadadadev.mvvm_pokedex.presentation.PokemonListViewModel
import com.dadadadev.mvvm_pokedex.presentation.theme.MVVMPokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVVMPokedexTheme {
                val navController = rememberNavController()

                Scaffold {
                    NavHost(
                        modifier = Modifier
                            .padding(paddingValues = it),
                        navController = navController,
                        startDestination = "pokemon_list_screen"
                    ) {
                        composable("pokemon_list_screen") {
                            PokemonListScreen(navController)
                        }
                        composable(
                            "pokemon_detail_screen/{dominantColor}/{pokemonName}",
                            arguments = listOf(
                                navArgument("dominantColor") {
                                    type = NavType.IntType
                                },
                                navArgument("pokemonName") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val dominantColor = remember {
                                val color = it.arguments?.getInt("dominantColor")
                                color?.let { Color(it) } ?: Color.White
                            }
                            val pokemonName = remember {
                                it.arguments?.getString("pokemonName")
                            }
                        }
                    }
                }
            }
        }
    }
}