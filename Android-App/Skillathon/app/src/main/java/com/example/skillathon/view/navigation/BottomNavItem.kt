package com.example.skillathon.view.navigation

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material.icons.sharp.Bookmark
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material.icons.sharp.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Sharp.Home)
    object Applied : BottomNavItem("applied", "Applied", Icons.Sharp.Bookmark)
    object Profile : BottomNavItem("profile", "Profile", Icons.Sharp.AccountCircle)
}
