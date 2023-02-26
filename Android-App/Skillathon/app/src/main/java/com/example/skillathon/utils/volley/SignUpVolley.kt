package com.example.skillathon.utils.volley

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.skillathon.constants.APIUrls
import com.example.skillathon.constants.Routes
import com.example.skillathon.utils.PleaseWaitDialog
import org.json.JSONObject
import java.nio.charset.Charset

class SignUpVolley(var context: Context) {
    private lateinit var volleyInstance: VolleyUtil
    init {
        volleyInstance = VolleyUtil(context = context)
    }

    fun signUp(name: String, email: String, mobile: String, passWord: String,
               gender:String,
               pleaseWaitDialogDisplay: MutableState<Boolean>,navController: NavHostController){
        val queue = volleyInstance.getQueue()
        val signUpUrl = APIUrls.SIGN_UP_URL

        val requestBody = "name=${name}&email=${email}&mobile=${mobile}&password=${passWord}&gender=${gender}"

        val stringRequest = object : StringRequest(
            Method.POST, signUpUrl,
            Response.Listener { response ->
                Toast.makeText(context, "Sign Up Successful! Verify your email Id to Log In", Toast.LENGTH_LONG).show()
                pleaseWaitDialogDisplay.value = false
                navController.navigate(Routes.LOG_IN_ROUTE){
                    popUpTo(Routes.SIGN_UP_ROUTE){
                        inclusive=true
                    }
                }
            },
            Response.ErrorListener { error ->
                Log.d("ERROR", error.networkResponse.data.decodeToString())
                val errJsonObj = JSONObject(error.networkResponse.data.decodeToString())
                Toast.makeText(context, errJsonObj["description"].toString(), Toast.LENGTH_LONG).show()
                pleaseWaitDialogDisplay.value = false
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray(Charset.forName("UTF-8"))
            }
        }
        queue.add(stringRequest)
    }
}