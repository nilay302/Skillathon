package com.example.skillathon.view.pages

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.skillathon.view.components.CustomButton
import com.example.skillathon.view.components.CustomPasswordTextField
import com.example.skillathon.view.components.CustomTextField
import com.example.skillathon.R
import com.example.skillathon.constants.Routes
import com.example.skillathon.utils.LogInUtil
import com.example.skillathon.utils.TokenValidator
import com.example.skillathon.view.components.PleaseWaitDialog

@Composable
fun LoginPage(navController: NavHostController) {
    val context = LocalContext.current
    if(TokenValidator().isTokenValid(context = context)){
        navController.navigate(Routes.HOME_PAGE_ROUTE){
            popUpTo(Routes.LOG_IN_ROUTE){
                inclusive= true
            }
        }
    }

    var pleaseWaitDialogDisplay = remember {
        mutableStateOf(false)
    }

    if(pleaseWaitDialogDisplay.value){
        PleaseWaitDialog()
    }

    var email = remember {
        mutableStateOf("")
    }

    var password = remember {
        mutableStateOf("")
    }

    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(top = 16.dp)
    ) {
        Image(painter = painterResource(R.drawable.gurukul),
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentDescription = "App Logo")

        Text(text = "Welcome Back!", fontSize=28.sp,
            color = colorResource(id = R.color.heading_text_color),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 32.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(text = "Log In to your account",
            color = colorResource(id = R.color.sub_heading_text_color),
        )

        Column(
            modifier = Modifier.padding(top = 36.dp, bottom = 24.dp)
        ) {
            CustomTextField(field = email,
                labelText = "Email",
                placeHolderText = "email",
                textType = KeyboardType.Email)

            Spacer(modifier = Modifier.height(16.dp))

            CustomPasswordTextField(field = password,
                labelText = "Password",
                placeHolderText = "password",)
        }

        CustomButton(text = "Log In", onClick = {
            LogInUtil().logIn(email.value, password.value, navController, context, pleaseWaitDialogDisplay)
        })

        Row(modifier = Modifier.padding(top = 24.dp)) {
            Text(text = "Don't have an account? ", fontWeight = FontWeight.Light, color = Color(0xffc5c8cd))
            Text(text = "Sign Up",
                fontWeight = FontWeight.SemiBold ,
                color = colorResource(id = R.color.blue_color_theme),
                modifier = Modifier.clickable {
                    gotoSignUpPage(navController)
                }
            )
        }

    }
}

fun gotoSignUpPage(navController: NavHostController){
    navController.navigate(Routes.SIGN_UP_ROUTE)
}
