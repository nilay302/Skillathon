package com.example.skillathon.utils.volley

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.skillathon.constants.APIUrls
import com.example.skillathon.models.ScholarshipItem
import com.example.skillathon.models.User
import com.example.skillathon.utils.storage.LocalStorage
import org.json.JSONObject

class ScholarshipListVolley(var context: Context) {
    private lateinit var volleyInstance: VolleyUtil
    init {
        volleyInstance = VolleyUtil(context = context)
    }

    fun getScholarshipDetails(scholarshipItem:MutableState<ScholarshipItem>,
                             id: String){
        val url = APIUrls.SCHOLARSHIP_LIST_URL+"/${id}"
        val queue = volleyInstance.getQueue()
        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                val jsonObj = JSONObject(response).getJSONObject("content")
                val categoryList = jsonObj.getJSONArray("categories")
                val categories = mutableListOf<String>()
                for(i in 0 until categoryList.length()){
                    categories.add(categoryList.getString(i))
                }
                val item = ScholarshipItem(
                    jsonObj.getString("_id"),
                    jsonObj.getString("name"),
                    categories,
                    jsonObj.optString("description", ""),
                    jsonObj.optString("gender", ""),
                    jsonObj.optString("course", ""),
                    jsonObj.optString("branch", ""),
                    jsonObj.optString("deadline", ""),
                    jsonObj.getLong("income"),
                    jsonObj.optString("amount", ""),
                    jsonObj.optString("provider_name", "")
                )
                scholarshipItem.value = item
            },
            Response.ErrorListener { error ->
                Log.d("Error", error.networkResponse.data.decodeToString())
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                val token = LocalStorage(context = context).getToken()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
        queue.add(stringRequest)
    }

    fun getScholarshipList(
        scholarshipList:MutableList<ScholarshipItem>,
        myScholarships:MutableList<String>,
        flag:MutableState<Boolean>
    ){
        if(scholarshipList.size > 0){
            flag.value = true
            return
        }
        val url = APIUrls.SCHOLARSHIP_LIST_URL
        val queue = volleyInstance.getQueue()
        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                val jsonList = JSONObject(response).getJSONArray("content")
                val myLists = JSONObject(response)
                    .getJSONObject("myScholarships")
                    .getJSONArray("applied")

                for(i in 0 until myLists.length()){
                    val jsonObj = myLists.getJSONObject(i)
                    myScholarships.add(jsonObj.getString("id"))
                }

                for(i in 0 until jsonList.length()){
                    Log.d("Object $i", jsonList.getJSONObject(i).toString())
                    val jsonObj = jsonList.getJSONObject(i)
                    val categoryList = jsonObj.getJSONArray("categories")
                    val categories = mutableListOf<String>()
                    for(i in 0 until categoryList.length()){
                        categories.add(categoryList.getString(i))
                    }
                    val item = ScholarshipItem(
                        jsonObj.getString("_id"),
                        jsonObj.getString("name"),
                        categories,
                        jsonObj.optString("description", ""),
                        jsonObj.optString("gender", ""),
                        jsonObj.optString("course", ""),
                        jsonObj.optString("branch", ""),
                        jsonObj.optString("deadline", ""),
                        jsonObj.getLong("income"),
                        jsonObj.optString("amount", ""),
                        jsonObj.optString("provider_name","")
                    )
                    scholarshipList.add(item)
                }
                flag.value = true
            },
            Response.ErrorListener { error ->
                Log.d("Error", error.networkResponse.data.decodeToString())
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                val token = LocalStorage(context = context).getToken()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
        queue.add(stringRequest)
    }
}

