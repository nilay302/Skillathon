package com.example.skillathon.view.pages

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.skillathon.R
import com.example.skillathon.constants.Routes
import com.example.skillathon.models.ScholarshipItem
import com.example.skillathon.utils.volley.ScholarshipListVolley
import com.example.skillathon.view.components.CircleDot

@Composable
fun ScholarshipListPage(navController:NavHostController){
    var myScholarships = remember {
        mutableListOf<String>()
    }
    var scholarshipList = rememberSaveable{
        mutableListOf<ScholarshipItem>()
    }
    var flag = rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    var query by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Scholarships",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 100.dp),
                fontFamily = FontFamily(Font(R.font.opensans_bold)))
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier.size(25.dp)) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "")
            }
        }
        Box(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = query,
                enabled = false,
                onValueChange = {
                    query = it
                                },
                colors=
                TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
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
                        color = colorResource(id = R.color.name_text_color)
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
                        color = colorResource(id = R.color.icon_color_card),
                        shape = RoundedCornerShape(10.dp)
                    )
            )
        }

        if(!flag.value)
            ScholarshipListVolley(context = context).getScholarshipList(scholarshipList,
                myScholarships,flag)

        if(flag.value) {
            LazyColumn {
                items(scholarshipList.size) { index ->
                    ScholarShipCard(
                        scholarshipList[index],
                        navController,myScholarships
                    )
                }
            }
        }
    }
}

@Composable
fun ScholarShipCard(
    scholarshipItem: ScholarshipItem,
    navController: NavHostController,
    myScholarships:MutableList<String>
){
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clickable {
                if(myScholarships.contains(scholarshipItem.id)){
                    Toast.makeText(context, "Already applied!", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate(Routes.SCHOLARSHIP_DETAILS_PAGE + "/${scholarshipItem.id}")
                }
            },
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
                        Text(text = scholarshipItem.name.ifEmpty { "No Name Given" },
                            fontFamily = FontFamily(Font(R.font.opensans_medium)),
                            fontSize = 20.sp
                        )
                        Text(
                            text = "2023-2024",
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
                    Text(text = scholarshipItem.amount.ifEmpty { "Not Given" },
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_light))
                    )
                }
                Column() {
                    Text(text = "End Date",
                        fontFamily = FontFamily(Font(R.font.opensans_medium)),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(text = scholarshipItem.deadline.ifEmpty { "Not Given" },
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.opensans_light))
                    )
                }
            }

            Text(text = scholarshipItem.provider.ifEmpty { "Provider name not available" },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp),
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.opensans_light))
            )
        }
    }
}