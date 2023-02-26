package com.example.skillathon.utils.volley

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavHostController
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.skillathon.constants.APIUrls
import com.example.skillathon.constants.Routes
import com.example.skillathon.utils.storage.LocalStorage
import org.json.JSONObject
import java.nio.charset.Charset

class ApplyScholarshipVolley(var context: Context) {
    private lateinit var volleyInstance: VolleyUtil
    init {
        volleyInstance = VolleyUtil(context = context)
    }

    fun apply(name:String,
              email:String,
              why_apply:String,
              aadhar:String,
              caste:String,
              userId:String,
              scholarshipId:String,
              navController:NavHostController
    ){
        val queue = volleyInstance.getQueue()
        val url = APIUrls.APPLY_SCHOLARSHIP_URL

        val requestBody = "name=${name}&email=${email}" +
                "&why_apply=${why_apply}&aadhar_card=${aadhar}&caste_certificate=${caste}" +
                "&_id=${userId}&scholarship_id=${scholarshipId}"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
               Toast.makeText(context, "Applied Successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.HOME_PAGE_ROUTE) {
                    popUpTo(Routes.LOG_IN_ROUTE){
                        inclusive = true
                    }
                }
            },
            Response.ErrorListener { error ->
                Log.d("ERROR", error.networkResponse.data.decodeToString())
                val errJsonObj = JSONObject(error.networkResponse.data.decodeToString())
                Toast.makeText(context, "You are not eligible", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                val token = LocalStorage(context).getToken()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
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