package com.up.meal.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.up.meal.presentation.favoriteScreen.FavoriteMealsScreen
import com.up.meal.presentation.mealDetail.MealDetailScreen
import com.up.meal.presentation.mealList.MealListScreen
import com.up.meal.presentation.ui.theme.MealAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.MealListScreen.route) {

        composable(route = NavScreen.MealListScreen.route) {
            MealListScreen(
                viewModel = hiltViewModel(),
                favoriteMealViewModel = hiltViewModel(),
                onMealCardClicked = { mealId ->
                    navController.navigate("${NavScreen.MealDetailScreen.route}/$mealId")
                },
                onViewAllClicked = {
                    navController.navigate(NavScreen.FavoriteMealsScreen.route)
                })
        }

        composable(
            route = NavScreen.MealDetailScreen.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.MealDetailScreen.argument0) { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val mealId =
                navBackStackEntry.arguments?.getString(NavScreen.MealDetailScreen.argument0)
                    ?: return@composable
            MealDetailScreen(
                viewModel = hiltViewModel(),
                ingredientViewModel = hiltViewModel(),
                mealId = mealId,
                onBackPressed = { navController.navigateUp() })
        }

        composable(
            route = NavScreen.FavoriteMealsScreen.route
        ) {
            FavoriteMealsScreen(
                viewModel = hiltViewModel(),
                onMealCardClicked = { navController.navigate("${NavScreen.MealDetailScreen.route}/$it") },
                onBackPressed = { navController.navigateUp() })
        }
    }

}

sealed class NavScreen(val route: String) {
    object MealListScreen : NavScreen("MealListScreen")
    object MealDetailScreen : NavScreen("MealDetailScreen") {
        const val routeWithArgument: String = "MealDetailScreen/{mealId}"
        const val argument0: String = "mealId"
    }

    object FavoriteMealsScreen : NavScreen("FavoriteMealsScreen")
}