package com.example.skillathon.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skillathon.view.pages.bottomNavPages.Applied
import com.example.skillathon.view.pages.bottomNavPages.Home
import com.example.skillathon.view.pages.bottomNavPages.Profile

@Composable
fun BottomNavigationGraph(
    bottomNavController: NavHostController,
    navController: NavHostController,
) {
    NavHost(bottomNavController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            Home(navController)
        }
        composable(BottomNavItem.Applied.route) {
            Applied(navController)
        }
        composable(BottomNavItem.Profile.route) {
            Profile(navController = navController)
        }
    }
}