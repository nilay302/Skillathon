package com.example.skillathon.view.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.skillathon.R
import com.example.skillathon.models.User
import com.example.skillathon.utils.ProfileUtil
import com.example.skillathon.utils.volley.ProfileVolley
import com.example.skillathon.view.components.CustomButton
import com.example.skillathon.view.components.CustomTextField
import com.example.skillathon.view.components.CustomTextFieldDisabled
import java.util.*
import kotlin.math.exp

@Composable
fun PersonalInfo(navController:NavHostController){
    val user: MutableState<User> = remember{
        mutableStateOf(
            User("","","",
                "","","","",
                "","","","", "")
        )

    }
    val context = LocalContext.current
    ProfileUtil().getProfile(
        context = context,
        navController=navController,
        user
    )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var name = remember{
                mutableStateOf("")
            }
            var email = remember{
                mutableStateOf("")
            }
            var mobile = remember {
                mutableStateOf("")
            }
            var gender = remember {
                mutableStateOf("")
            }
            var flag = remember {
                mutableStateOf(false)
            }
            var expandedCategoryDD = remember { mutableStateOf(false) }
            var selectedCategory = remember { mutableStateOf("Open") }
            val categoryOptions = listOf("open", "obc", "sbc", "vj", "nt-a",
                "nt-b","nt-c","nt-d",
                "sc", "sd")

            if(user.value._id.isNotEmpty()) {
                if(!flag.value) {
                    name.value = user.value.name
                    email.value = user.value.email
                    mobile.value = user.value.mobile
                    gender.value = "Male"
                    selectedCategory.value = user.value.category.uppercase(Locale.ENGLISH)
                }
                flag.value = true
            }

            Text(text = "Personal Information",
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                fontSize = 24.sp,
                fontWeight = FontWeight.W600
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomTextFieldDisabled(field = name, labelText = "Name",
                placeHolderText = "name", textType = KeyboardType.Text)

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextFieldDisabled(field = email, labelText = "Email",
                placeHolderText = "email", textType = KeyboardType.Email)

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextFieldDisabled(field = gender, labelText = "Gender",
                placeHolderText = "Male", textType = KeyboardType.Text)

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(field = mobile, labelText = "Mobile",
                placeHolderText = "mobile", textType = KeyboardType.Phone)

            Spacer(modifier = Modifier.height(16.dp))

            Box {
                DropdownMenu(
                    expanded = expandedCategoryDD.value,
                    onDismissRequest = { expandedCategoryDD.value = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categoryOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                selectedCategory.value = option
                                expandedCategoryDD.value = false
                            },
                        ) {
                            Text(option)
                        }
                    }
                }

                Row (
                    horizontalArrangement = Arrangement.SpaceBetween
                    ,modifier = Modifier
                        .clickable { expandedCategoryDD.value = true }
                        .padding(8.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp),){
                    Text(
                        text = selectedCategory.value,
                        fontSize = 20.sp,
                        color = if (expandedCategoryDD.value)
                            colorResource(id = R.color.heading_text_color) else Color.Gray,
                    )
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                }

            }

            user.value.gender = gender.value.toUpperCase()
            user.value.category = selectedCategory.value.toLowerCase()
            Spacer(modifier = Modifier.height(48.dp))
            CustomButton(text = "Update") {
                ProfileVolley(context).updateProfile(
                    user.value,
                    navController
                )
            }
        }

}