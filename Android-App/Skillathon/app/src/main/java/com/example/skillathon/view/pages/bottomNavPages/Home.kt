package com.example.skillathon.view.pages.bottomNavPages
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import com.example.skillathon.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.sharp.School
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.skillathon.constants.Routes
import com.example.skillathon.view.components.CircleDot

@Composable
fun Home(navController: NavHostController){
    var query by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = query,
                onValueChange = { query = it },
                colors=
                TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Gray,
                    focusedLabelColor = Color(0xff0073b1),
                    cursorColor = Color(0xff0073b1),
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black
                ),
                placeholder = {
                    Text(
                        text = "Search...",
                        color = Color.Gray
                    ) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(10.dp)
                    )
            )
        }

        Row (modifier = Modifier.padding(24.dp)){
           Text(text = "For you",
               fontSize = 18.sp,
               fontFamily = FontFamily(Font(R.font.opensans_bold)))
        }

        FeaturedCard()

        Divider(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp))

        Row(modifier = Modifier.padding(vertical = 32.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            fun goToScholarshipsList(){
                navController.navigate(Routes.SCHOLARSHIP_LIST_PAGE)
            }
            AppCard(imageVector = Icons.Filled.School, title = "Scholarships") { goToScholarshipsList() }
            AppCard(imageVector = Icons.Filled.PeopleAlt, title ="Mentors"){goToScholarshipsList()}
        }
    }

}

@Composable
fun AppCard(imageVector: ImageVector, title: String, onClick:()->Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
        onClick()
    }) {
        Card(elevation = 10.dp, backgroundColor = colorResource(id = R.color.option_card_color),
            modifier =
            Modifier.clip(RoundedCornerShape(size = 16.dp))
        ) {
            Icon(imageVector=imageVector, contentDescription = "Icon",
                modifier = Modifier
                    .padding(20.dp)
                    .size(100.dp),
                tint = colorResource(id = R.color.icon_color_card)
            )
        }
        Text(
            text = title,
            modifier=Modifier.padding(top = 16.dp),
            fontFamily =  FontFamily(Font(R.font.opensans_bold),
            )
        )
    }
}


@Composable
fun FeaturedCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircleDot(radius = 25.dp)
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(text = "Scholarship Name",
                            fontFamily = FontFamily(Font(R.font.opensans_medium)),
                            fontSize = 20.sp
                            )
                        Text(
                            text = "2022-2023",
                            fontFamily = FontFamily(Font(R.font.opensans_regular)),
                            fontSize = 12.sp
                        )
                    }
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(text = "Amount",
                        fontFamily = FontFamily(Font(R.font.opensans_medium)),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(text = "Rs.40000",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_light))
                    )
                }
                Column() {
                    Text(text = "End Date",
                        fontFamily = FontFamily(Font(R.font.opensans_medium)),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(text = "31/03/2023",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_light))
                    )
                }
            }

            Text(text = "Provider Name",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp),
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.opensans_light))
            )
        }
    }
}



