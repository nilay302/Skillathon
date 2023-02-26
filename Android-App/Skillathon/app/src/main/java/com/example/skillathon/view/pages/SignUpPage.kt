package com.example.skillathon.view.pages

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.skillathon.view.components.CustomPasswordTextField
import com.example.skillathon.view.components.CustomTextField
import com.example.skillathon.R
import com.example.skillathon.constants.Routes
import com.example.skillathon.utils.SignUpUtil
import com.example.skillathon.view.components.CustomButton
import com.example.skillathon.view.components.PleaseWaitDialog

@Composable
fun SignUpPage(
    navController: NavHostController,
    context:Context
) {
    var pleaseWaitDialogDisplay = remember {
        mutableStateOf(false)
    }

    if(pleaseWaitDialogDisplay.value){
        PleaseWaitDialog()
    }

    var name = remember {
        mutableStateOf("")
    }

    var email = remember {
        mutableStateOf("")
    }

    var userName = remember {
        mutableStateOf("")
    }

    var password = remember {
        mutableStateOf("")
    }

    var confirmPassword = remember {
        mutableStateOf("")
    }

    var mobile = remember {
        mutableStateOf("")
    }

    val genderOptions = listOf("Male", "Female", "Other")
    var selectedGender = remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.verticalScroll(scrollState).padding(top = 8.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.gurukul),
            modifier = Modifier.size(80.dp)
                .clip(CircleShape),
            contentDescription = "App Logo")

        Text(text = "Welcome!", fontSize=36.sp,
            color = colorResource(id = R.color.heading_text_color),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top=8.dp)
        )

        Text(text = "Create your account",
            color = colorResource(id = R.color.sub_heading_text_color),
        )

        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            CustomTextField(field = name,
                labelText = "Name",
                placeHolderText = "name",
                textType = KeyboardType.Text)

            Spacer(modifier = Modifier.height(6.dp))

            CustomTextField(field = email,
                labelText = "Email",
                placeHolderText = "email",
                textType = KeyboardType.Email)

            Spacer(modifier = Modifier.height(6.dp))

            CustomTextField(field = mobile,
                labelText = "Mobile",
                placeHolderText = "mobile no.",
                textType = KeyboardType.Phone)

            Spacer(modifier = Modifier.height(6.dp))

            Spacer(modifier = Modifier.height(6.dp))

            CustomPasswordTextField(field = password,
                labelText = "Password",
                placeHolderText = "password",)

            Spacer(modifier = Modifier.height(6.dp))

            CustomPasswordTextField(field = confirmPassword,
                labelText = "Confirm Password",
                placeHolderText = "password")
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            genderOptions.forEach { text ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RadioButton(
                        selected = selectedGender.value == text.lowercase(),
                        onClick = { selectedGender.value = text.lowercase() },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.blue_color_theme)
                        )
                    )
                    Text(text, modifier = Modifier.padding(start = 16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        CustomButton(text = "Sign Up", onClick = {
            SignUpUtil().signUp(
                name = name.value,
                email=email.value,
                mobile=mobile.value,
                password = password.value,
                confirmPassword=confirmPassword.value,
                gender=selectedGender.value,
                navController = navController,
                context = context,
                pleaseWaitDialogDisplay=pleaseWaitDialogDisplay
            )
        })
        
        Row(modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Already have an account? ", fontWeight = FontWeight.Light, color = Color(0xffc5c8cd))
            Text(text = "Log In",
                fontWeight = FontWeight.SemiBold ,
                color = colorResource(id = R.color.blue_color_theme),
                modifier = Modifier.clickable {
                    gotoLogInPage(navController)
                }
            )
        }
        
    }
}

fun gotoLogInPage(navController: NavHostController){
    navController.navigate(Routes.LOG_IN_ROUTE){
        popUpTo(Routes.SIGN_UP_ROUTE){
            inclusive
        }
    }
}