package com.example.skillathon.view.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.skillathon.view.components.CustomButton

@Composable
fun ScholarshipDetails(scholarshipId: String, navController: NavHostController){
    val scholarshipItem = remember{
        mutableStateOf<ScholarshipItem>(ScholarshipItem("","",
            mutableListOf(), "","","","","",
            0,"",""))
    }
    ScholarshipListVolley(LocalContext.current).getScholarshipDetails(
        scholarshipItem,scholarshipId
    )
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "About Scholarship",
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Name",
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 20.sp
        )
        Text(
            text = scholarshipItem.value!!.name.ifEmpty { "Not available" },
            fontFamily = FontFamily(Font(R.font.opensans_regular)),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Divider(modifier = Modifier
            .height(1.dp))

        Text(
            text = "Description",
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 20.sp
        )
        Text(
            text = scholarshipItem.value!!.description.ifEmpty { "Not available" },
            fontFamily = FontFamily(Font(R.font.opensans_regular)),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier
            .height(1.dp))

        Text(
            text = "Provider Name",
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 20.sp
        )
        Text(
            text = scholarshipItem.value!!.provider.ifEmpty { "Not available" },
            fontFamily = FontFamily(Font(R.font.opensans_regular)),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier
            .height(1.dp))

        Text(
            text = "Amount",
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 20.sp
        )
        Text(
            text = scholarshipItem.value!!.amount.ifEmpty { "Not available" },
            fontFamily = FontFamily(Font(R.font.opensans_regular)),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier
            .height(1.dp))


        Text(
            text = "Deadline",
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 20.sp
        )
        Text(
            text = scholarshipItem.value!!.deadline.ifEmpty { "Not available" },
            fontFamily = FontFamily(Font(R.font.opensans_regular)),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier
            .height(3.dp))

        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Eligibility",
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Gender",
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 20.sp
        )
        Text(
            text = scholarshipItem.value!!.gender.ifEmpty { "Any gender can apply" },
            fontFamily = FontFamily(Font(R.font.opensans_regular)),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier
            .height(1.dp))

        Text(
            text = "Categories",
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 20.sp
        )
        if(scholarshipItem.value!!.categories.isEmpty()){
            Text(
                text = scholarshipItem.value!!.amount.ifEmpty { "All Category people can apply" },
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                fontSize = 16.sp
            )
        } else {
            var str = ""
            for(i in 0 until scholarshipItem.value!!.categories.size){
                str += scholarshipItem.value!!.categories[i]
                if(i != scholarshipItem.value!!.categories.size-1){
                    str += ", "
                }
            }
            Text(
                text = str,
                fontFamily = FontFamily(Font(R.font.opensans_regular)),
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier
            .height(1.dp))

        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(text = "Apply") {
            navController.navigate(Routes.SCHOLARSHIP_APPLY+"/${scholarshipId}")
        }

    }
}