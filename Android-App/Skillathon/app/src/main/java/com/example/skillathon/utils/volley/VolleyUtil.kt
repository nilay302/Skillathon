package com.example.skillathon.utils.volley

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyUtil(var context: Context) {
    private var volleyQueue: RequestQueue? = null
    fun getQueue(): RequestQueue{
        if(volleyQueue == null){
            volleyQueue = Volley.newRequestQueue(context)
        }
        return volleyQueue!!
    }
}


