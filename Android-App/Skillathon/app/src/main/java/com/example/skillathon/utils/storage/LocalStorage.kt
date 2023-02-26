package com.example.skillathon.utils.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.skillathon.constants.UserData

class LocalStorage(context: Context){
    var pref : SharedPreferences? = null
    init {
        pref = context.getSharedPreferences(UserData.USERPREF, Context.MODE_PRIVATE)
    }

    fun saveId(id: String){
        val editor = pref!!.edit()
        editor.putString(UserData.USER_ID, id)
        editor.apply()
    }

    fun getId(): String?{
        return pref!!.getString(UserData.USER_ID, "")
    }

    fun saveToken(token: String){
        val editor = pref!!.edit()
        editor.putString(UserData.TOKEN, token)
        editor.apply()
    }

    fun getToken(): String?{
        return pref!!.getString(UserData.TOKEN, "")
    }
}