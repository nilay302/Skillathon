package com.example.skillathon.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.skillathon.constants.Routes
import com.example.skillathon.models.User
import com.example.skillathon.utils.storage.LocalStorage
import com.example.skillathon.utils.volley.ProfileVolley
import okhttp3.Route

class ProfileUtil{
    fun getProfile(
        context: Context,
        navController: NavHostController,
        user: MutableState<User>
    ){
        val token = LocalStorage(context = context).getToken()
        if(token == null){
            Toast.makeText(context, "Please Login Again!", Toast.LENGTH_SHORT).show()
            navController.navigate(Routes.LOG_IN_ROUTE){
                popUpTo(Routes.HOME_PAGE_ROUTE){
                    inclusive = true
                }
            }
        } else {
            ProfileVolley(context = context).getProfile(token, user)
        }
    }
}