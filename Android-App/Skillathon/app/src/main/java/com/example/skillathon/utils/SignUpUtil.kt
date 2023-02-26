package com.example.skillathon.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.example.skillathon.constants.Routes
import com.example.skillathon.utils.volley.SignUpVolley

class SignUpUtil {
    fun signUp(name: String?,
               email: String?,
               mobile: String?,
               password: String?,
               confirmPassword: String?,
               gender:String?,
               navController: NavHostController,
               context: Context,
               pleaseWaitDialogDisplay: MutableState<Boolean>
    ) {
        val areCredentialsValid =
            SignUpCredentialsValidator().validate(name, email, mobile, password, confirmPassword, gender,context = context)
        if(areCredentialsValid){
            /**TODO
             * Log In Logic
             */
            pleaseWaitDialogDisplay.value = true
            SignUpVolley(context = context).signUp(name!!, email!!, mobile!!, password!!, gender!!,pleaseWaitDialogDisplay, navController)
        } else {
            /** TODO
             * show error
             */
        }
    }
}


class SignUpCredentialsValidator{
    fun validate(name: String?,
                 email: String?,
                 mobile: String?,
                 password: String?,
                 confirmPassword: String?,
                 gender:String?,
                 context: Context
    ): Boolean{
        if(name.isNullOrEmpty()
            || email.isNullOrEmpty()
            || mobile.isNullOrEmpty()
            || password.isNullOrEmpty()
            || confirmPassword.isNullOrEmpty()
            || gender.isNullOrEmpty()
        ) {
            Toast.makeText(context, "No field can be empty!", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"))){
            Toast.makeText(context, "Invalid Email Format", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!mobile.matches(Regex("[0-9]{10}"))){
            Toast.makeText(context, "Please Enter a 10-digit Valid Mobile Number", Toast.LENGTH_SHORT).show()
            return false
        }

        if(!password.matches(Regex("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"))){
            Toast.makeText(context, "Password should be at least 8 characters with lowercase, uppercase, digit and a special character!", Toast.LENGTH_SHORT).show()
            return false
        }

        if(password != confirmPassword){
            Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true ;
    }
}