package com.example.skillathon.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.skillathon.models.User
import com.example.skillathon.utils.storage.LocalStorage
import com.example.skillathon.utils.volley.ProfileVolley
import com.example.skillathon.view.components.CustomButton
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

@Composable
fun ImageUpload(
    navController: NavHostController
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it)
            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver,it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let {  btm ->
                Image(bitmap = btm.asImageBitmap(),
                    contentDescription =null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape))
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(text = "Pick Image",onClick = {
            launcher.launch("image/*")
        })
        
        Spacer(modifier = Modifier.height(32.dp))
        CustomButton(text = "Upload Profile Picture",onClick = {
            if(imageUri==null){
                Toast.makeText(context, "No Image Selected!", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseUpload(context, imageUri!!).uploadImage(navController)
            }
        })
    }
}

fun getFileExtensionFromUri(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    val mimeTypeMap = MimeTypeMap.getSingleton()
    val extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    return if (extension != null) ".$extension" else null
}

class FirebaseUpload(var context: Context, var uri: Uri){
    fun uploadImage(navController: NavHostController) {
        val ref = Firebase.storage.reference
        val id = LocalStorage(context).getId()
        val extension = getFileExtensionFromUri(context, uri)
        var imageRef = ref.child("$id${extension}")
        imageRef.putFile(uri)
            .addOnSuccessListener { it ->
                it.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener {url->
                        val imageUrl = url!!.toString()
                        val user = User("","","","",
                            imageUrl, "","","","",
                            "","","")
                        ProfileVolley(context).updateProfile(user, navController)
                    }
                    .addOnFailureListener {it2->
                        it2.printStackTrace()
                    }
            }
            .addOnFailureListener{
                it.printStackTrace()
            }
    }
}
