package com.example.skillathon.view.pages
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.skillathon.view.navigation.BottomNavigation
import com.example.skillathon.view.navigation.BottomNavigationGraph

@Composable
fun HomePage(
    navController: NavHostController,
){
    val bottomNavController = rememberNavController()
    MaterialTheme() {
        Scaffold(bottomBar = { BottomNavigation(navController = bottomNavController)}) {
            Column(modifier = Modifier.padding(it)) {
                BottomNavigationGraph(
                    bottomNavController = bottomNavController,
                    navController = navController,
                )
            }
        }
    }
}

