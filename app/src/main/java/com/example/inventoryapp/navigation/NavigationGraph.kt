package com.example.inventoryapp.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventoryapp.screens.AddScreen
import com.example.inventoryapp.screens.AddScreenViewModel
import com.example.inventoryapp.screens.DetailScreen
import com.example.inventoryapp.screens.DetailScreenViewModel
import com.example.inventoryapp.screens.EditScreen
import com.example.inventoryapp.screens.EditScreenViewModel
import com.example.inventoryapp.screens.MainScreen
import com.example.inventoryapp.screens.MainScreenViewModel


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = HomeScreenDestination.route){

        composable(HomeScreenDestination.route){
            val mainScreenViewModel: MainScreenViewModel = hiltViewModel()
            MainScreen(modifier = modifier, viewModel = mainScreenViewModel,onAddButtonClicked = {
                navController.navigate(AddScreenDestination.route)
            }, onItemClick = {
                navController.navigate("${DetailDestination.route}/$it")
            })
        }
        composable(AddScreenDestination.route){
            val addScreenViewModel: AddScreenViewModel = hiltViewModel()
            AddScreen(addScreenViewModel, onAddButtonClicked = {
                addScreenViewModel.addData()
                Toast.makeText(
                    context,
                    "Data Added",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(HomeScreenDestination.route)
            }, navigateBack = {
                navController.navigateUp()
            })
        }
        composable(
            route = DetailDestination.routeWithArg,
            arguments = listOf(navArgument(DetailDestination.itemId){
            type = NavType.IntType
        })){
            val detailScreenViewModel: DetailScreenViewModel = hiltViewModel()
           DetailScreen(detailScreenViewModel, onClickSellButton = {detailScreenViewModel.sellItem()}, navigateBack = {
               navController.popBackStack()
           }, navigateToEditScreen = {
               navController.navigate(route = "${EditDestination.route}/$it")
           })
        }

        composable(
            route = EditDestination.routeWithArg,
            arguments = listOf(navArgument(EditDestination.itemId){
                type = NavType.IntType
            })){
            val editScreenViewModel: EditScreenViewModel = hiltViewModel()
            EditScreen(editScreenViewModel, onUpdateButtonClicked = {
                editScreenViewModel.updateItem()
                Toast.makeText(
                    context,
                    "Data Updated",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(HomeScreenDestination.route)
            }, navigateBack = {
                navController.navigateUp()
            })
        }

    }
    
}