package com.example.skillathon.view.pages

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toFile
import androidx.navigation.NavHostController
import com.example.skillathon.R
import com.example.skillathon.models.ScholarshipItem
import com.example.skillathon.models.User
import com.example.skillathon.utils.getFileExtensionFromUri
import com.example.skillathon.utils.storage.LocalStorage
import com.example.skillathon.utils.volley.ApplyScholarshipVolley
import com.example.skillathon.utils.volley.ProfileVolley
import com.example.skillathon.utils.volley.ScholarshipListVolley
import com.example.skillathon.view.components.CustomButton
import com.example.skillathon.view.components.CustomTextField
import com.example.skillathon.view.components.CustomTextFieldMultiLine
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@Composable
fun ScholarshipApply(id:String?,
                     navController:NavHostController){
    val context = LocalContext.current
    var name = remember {
        mutableStateOf("")
    }
    var email = remember {
        mutableStateOf("")
    }
    var why_apply = remember {
        mutableStateOf("")
    }
    var aadharLink = remember {
        mutableStateOf("")
    }
    var casteCertLink = remember {
        mutableStateOf("")
    }
    val aadharURI = remember { mutableStateOf<Uri?>(null) }
    val casteCertificateURI = remember { mutableStateOf<Uri?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Apply",
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Name",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 32.dp, bottom = 6.dp),
            fontFamily = FontFamily(Font(R.font.opensans_medium))
        )

        CustomTextField(
            field = name, labelText = "Name",
            placeHolderText = "name", textType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Email",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 32.dp, bottom = 6.dp),
            fontFamily = FontFamily(Font(R.font.opensans_medium))
        )

        CustomTextField(
            field = email, labelText = "Email",
            placeHolderText = "email", textType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Why do you need this scholarship?",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 32.dp, bottom = 6.dp),
            fontFamily = FontFamily(Font(R.font.opensans_medium))
        )

        CustomTextFieldMultiLine(
            field = why_apply, labelText = "",
            placeHolderText = "Add Info here", textType = KeyboardType.Text
        )

        val flag1 = remember {
            mutableStateOf(false)
        }
        val flag2 = remember {
            mutableStateOf(false)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        PDFSelector(aadharURI, aadharLink, if(flag1.value) "Aadhar Card selected" else "Upload aadhar", flag1)
        
        Spacer(modifier = Modifier.height(16.dp))
        PDFSelector(casteCertificateURI,casteCertLink,if(flag2.value) "Caste Certificate" else "Upload caste", flag2)

        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(text = "Apply") {
                ApplyScholarshipVolley(context).apply(
                    name.value,email.value, why_apply.value,
                    aadharLink.value,
                    casteCertLink.value,
                    LocalStorage(context).getId()!!,
                    scholarshipId = id!!,
                    navController
                )
        }


    }
}

fun uploadFirebase(uri:Uri,
                   fileUrl:MutableState<String>,
                   context: Context,flag:MutableState<Boolean>
) {
    val ref = Firebase.storage.reference
    val id = LocalStorage(context).getId()
    val extension = getFileExtensionFromUri(context, uri)
    var imageRef = ref.child("$id${extension}")
    imageRef.putFile(uri)
        .addOnSuccessListener { it ->
            it.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener {url->
                    fileUrl.value = url.toString()
                    Log.d("UPLOADED", url.toString())
                    flag.value = true
                }
                .addOnFailureListener {it2->
                    it2.printStackTrace()
                    Log.d("FAILED", "FAILED")
                }
        }
        .addOnFailureListener{
            it.printStackTrace()
        }
}


@Composable
fun PDFSelector(result:MutableState<Uri?>,
                fileLink:MutableState<String>,
                title:String, flag:MutableState<Boolean>) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        result.value = it
    }
    Box(
        modifier = Modifier.border(
            width = 1.dp,
            color = Color.Black,
            shape = RoundedCornerShape(size = 12.dp)
        )
    ) {
        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 32.dp)
        ){
            IconButton(onClick = {
                launcher.launch(arrayOf("application/pdf"))
            }) {
                Image(
                    painter = painterResource(id = R.drawable.pdf_icon),
                    modifier = Modifier.size(50.dp),
                    contentDescription = ""
                )
            }
            Text(text = title,
                modifier=Modifier.padding(start = 16.dp),
                fontFamily = FontFamily(Font(R.font.opensans_medium)))
        }
        val context = LocalContext.current
        if(result.value != null){
            uploadFirebase(result.value!!,
                fileLink, context, flag)
        }
    }

}
