package com.example.skillathon.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skillathon.constants.Routes
import com.example.skillathon.utils.ImageUpload
import com.example.skillathon.view.pages.*

@Composable
fun App(context: Context){
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.LOG_IN_ROUTE) {
        composable(Routes.LOG_IN_ROUTE) { LoginPage(navController)}
        composable(Routes.SIGN_UP_ROUTE) { SignUpPage(navController, context = context) }
        composable(Routes.HOME_PAGE_ROUTE){ HomePage(navController = navController)}
        composable(Routes.IMAGE_UPLOADER){ ImageUpload(navController = navController)}
        composable(Routes.PERSONAL_INFORMATION){ PersonalInfo(navController)}
        composable(Routes.SCHOLARSHIP_LIST_PAGE){ ScholarshipListPage(navController) }
        composable(Routes.SCHOLARSHIP_DETAILS_PAGE+"/{scholarshipId}",
            arguments = listOf(navArgument("scholarshipId") { type = NavType.StringType })
            ){
            val id = it.arguments?.getString("scholarshipId")
            ScholarshipDetails(scholarshipId = id!!,navController)
        }
        composable(Routes.SCHOLARSHIP_APPLY+"/{scholarshipId}",
            arguments = listOf(navArgument("scholarshipId") { type = NavType.StringType })
        ){
            val id = it.arguments?.getString("scholarshipId")
            ScholarshipApply(id!!,navController)
        }
    }
}