package com.example.skillathon.view.pages.bottomNavPages

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.skillathon.R
import com.example.skillathon.constants.Routes
import com.example.skillathon.constants.UserData
import com.example.skillathon.models.User
import com.example.skillathon.utils.ProfileUtil
import java.util.prefs.Preferences

@Composable
fun Profile(navController: NavHostController
){
    val user: MutableState<User> = remember{
        mutableStateOf(User("","","",
            "","","","",
        "","","","",""))

    }
    val context = LocalContext.current
    ProfileUtil().getProfile(
        context = context,
        navController=navController,
        user
    )
    if(user.value._id.isNotEmpty())
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            Text(text = "Profile and Account",
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                fontSize = 24.sp,
                fontWeight = FontWeight.W600
            )
            Spacer(Modifier.height(16.dp))
            Box{
                Image(painter = rememberAsyncImagePainter(model =
                user.value.profilePicLink.ifEmpty { "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSL60p-Cv5yRHtLK2z80SyuAFy8Qskexvs0AQ&usqp=CAU" }),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(130.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.FillBounds,
                )

                IconButton(onClick = {
                                     navController.navigate(Routes.IMAGE_UPLOADER)
                },
                    modifier = Modifier
                        .offset(x = 18.dp, y = 100.dp)
                        .clip(CircleShape)
                        .background(color = Color.White)
                        .size(30.dp)
                        .zIndex(10f)
                ) {
                    Icon(imageVector = Icons.Default.Photo,
                        contentDescription = "cameraIcon",
                        tint = Color.Black
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
            Text(
                text = user.value.name.ifEmpty { "Sunil Pal" },
                fontFamily = FontFamily(Font(R.font.opensans_medium)),
                fontSize = 24.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(id = R.color.name_text_color)
            )
            Text(text = user.value.collegeName.ifEmpty { "College not updated" },
                fontFamily = FontFamily(Font(R.font.opensans_medium)),
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(id = R.color.text_color_gray)
            )
            Spacer(Modifier.height(8.dp))
            Divider(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 32.dp))

            val verticalScrollState = rememberScrollState()

            Column(Modifier.verticalScroll(verticalScrollState),
                ) {
                InfoOption(imageVector = Icons.Default.People, title = "Personal Info"
                ) { goToPersonalInfo(navController) }
                InfoOption(imageVector = Icons.Default.DocumentScanner, title = "Resume and My Info"){ goToPersonalInfo(navController) }

                InfoOption(imageVector = Icons.Default.Settings, title = "Log Out"){
                    val pref = context.getSharedPreferences(UserData.USERPREF, Context.MODE_PRIVATE)
                    pref.edit().clear().apply()
                    navController.navigate(Routes.LOG_IN_ROUTE){

                    }
                }
            }
        }
}

fun goToPersonalInfo(navController: NavHostController){
    navController.navigate(Routes.PERSONAL_INFORMATION)
}

@Composable
fun InfoOption(
    imageVector: ImageVector,
    title: String,
    onClick: (() -> Unit)
){
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .clickable {
                onClick()
            }) {
        Row(){
            Card(elevation = 10.dp, backgroundColor = colorResource(id = R.color.option_card_color),
                modifier = Modifier.clip(RoundedCornerShape(size = 12.dp))
            ) {
                Icon(imageVector=imageVector, contentDescription = "Icon",
                    modifier = Modifier.padding(20.dp),
                    tint = colorResource(id = R.color.icon_color_card)
                )
            }
            Text(text = title,
                fontFamily = FontFamily(Font(R.font.opensans_medium)),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 24.dp)
                    .align(Alignment.CenterVertically))
        }
        Icon(imageVector=Icons.Outlined.KeyboardArrowRight,
            contentDescription = "forward arrow",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(40.dp),
        )
    }
}
