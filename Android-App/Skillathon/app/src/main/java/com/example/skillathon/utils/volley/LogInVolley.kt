package com.example.skillathon.utils.volley

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.skillathon.constants.APIUrls
import com.example.skillathon.constants.Routes
import com.example.skillathon.utils.storage.LocalStorage
import org.json.JSONObject
import java.nio.charset.Charset

class LogInVolley(var context: Context) {
    private lateinit var volleyInstance: VolleyUtil
    init {
        volleyInstance = VolleyUtil(context = context)
    }

    fun logIn(email: String, passWord: String,
               pleaseWaitDialogDisplay: MutableState<Boolean>,
              navController: NavHostController
    ){
        val queue = volleyInstance.getQueue()
        val signUpUrl = APIUrls.LOG_IN_URL

        val requestBody = "email=${email}&password=${passWord}"

        val stringRequest = object : StringRequest(
            Method.POST, signUpUrl,
            Response.Listener { response ->
                Toast.makeText(context, "Log In Successful", Toast.LENGTH_LONG).show()
                pleaseWaitDialogDisplay.value = false
                val resJson = JSONObject(response)
                val token = resJson.getString("token")
                LocalStorage(context).saveToken(token)
                navController.navigate(Routes.HOME_PAGE_ROUTE) {
                    popUpTo(Routes.LOG_IN_ROUTE){
                        inclusive = true
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