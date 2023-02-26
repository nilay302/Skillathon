package com.example.skillathon.utils.volley

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.skillathon.constants.APIUrls
import com.example.skillathon.models.User
import com.example.skillathon.utils.storage.LocalStorage
import org.json.JSONObject
import java.nio.charset.Charset


class ProfileVolley(var context: Context) {
    private lateinit var volleyInstance: VolleyUtil
    init {
        volleyInstance = VolleyUtil(context = context)
    }

    fun getProfile(token: String, userState: MutableState<User>){
        val queue = volleyInstance.getQueue()
        val signUpUrl = APIUrls.SIGN_UP_URL
        val stringRequest = object : StringRequest(
            Method.GET, signUpUrl,
            Response.Listener { response ->
                Log.d("Success", response)
                val resJson = JSONObject(response).getJSONObject("content")
                val id = resJson.getString("_id")
                LocalStorage(context).saveId(id)
                val user = User(
                    resJson.getString("_id"),
                    resJson.getString("name"),
                    resJson.getString("email"),
                    resJson.getString("mobile"),
                    resJson.optString("profile_pic", ""),
                    resJson.optString("resume", ""),
                    resJson.optString("college_name", ""),
                    resJson.optString("course", ""),
                    resJson.optString("yearOfStudy", ""),
                    resJson.optString("branch", ""),
                    resJson.optString("gender",""),
                    resJson.optString("category","")
                )
                userState.value = user
            },
            Response.ErrorListener { error ->
                Log.d("Error", error.networkResponse.data.decodeToString())
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
        queue.add(stringRequest)
    }

    fun updateProfile(user: User, navController: NavHostController){
        val queue = volleyInstance.getQueue()
        val id = LocalStorage(context).getId()
        user._id = id!!
        val profileUpdateUrl = APIUrls.SIGN_UP_URL

        var requestBody = "_id=${user._id}&profile_pic=${user.profilePicLink}"
        if(user.category.isNotEmpty()){
            requestBody = "_id=${user._id}&category=${user.category}"
        }

        val stringRequest = object : StringRequest(
            Method.PUT, profileUpdateUrl,
            Response.Listener { response ->
                Toast.makeText(context, "Updated successfully!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            },
            Response.ErrorListener { error ->
                Log.d("ERROR", error.networkResponse.data.decodeToString())
                val errJsonObj = JSONObject(error.networkResponse.data.decodeToString())
                Toast.makeText(context, errJsonObj["description"].toString(), Toast.LENGTH_LONG).show()
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