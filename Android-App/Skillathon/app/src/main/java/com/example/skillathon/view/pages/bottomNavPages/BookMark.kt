package com.example.skillathon.view.pages.bottomNavPages

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.skillathon.R
import com.example.skillathon.constants.Routes
import com.example.skillathon.models.ScholarshipItem
import com.example.skillathon.view.components.CircleDot

@Composable
fun Applied(navController:NavHostController){

    val cats = mutableListOf<String>("open","obc","sbc",
        "nt-a","nt-b","nt-c","nt-d","sc","st")
    val scholarshipList = mutableListOf<ScholarshipItem>(
        ScholarshipItem("1", "Dream Scholarship", cats,
            "","male","","","2023-02-28",
            0,"Rs 1500", "Dream Foundation")
    )

    Column() {
        Text(text = "Applied",
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 32.dp, start = 32.dp)
        )

        LazyColumn {
            items(scholarshipList.size) { index ->
                ScholarShipCard(scholarshipList[index],
                    navController)
            }
        }

    }
}

@Composable
fun ScholarShipCard(
    scholarshipItem: ScholarshipItem,
    navController: NavHostController
){
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clickable {
                Toast.makeText(context, "Already applied!", Toast.LENGTH_SHORT).show()
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