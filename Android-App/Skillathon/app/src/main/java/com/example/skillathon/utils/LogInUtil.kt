package com.example.skillathon.utils
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.example.skillathon.constants.Routes
import com.example.skillathon.utils.storage.LocalStorage
import com.example.skillathon.utils.volley.LogInVolley
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.JWTParser
import java.util.*

class LogInUtil {
    fun logIn(email: String,
              password: String,
              navController: NavHostController,
              context: Context,
              pleaseWaitDialogDisplay: MutableState<Boolean>
    ) {
        val areCredentialsValid =
            LogInCredentialsValidator().validate(email, password, context)
        if(areCredentialsValid){
            /**TODO
             * Log In Logic
             */
            pleaseWaitDialogDisplay.value = true
            LogInVolley(context = context).logIn(email, password,
                pleaseWaitDialogDisplay, navController)
        } else {
            /** TODO
             * show error
             */
            Toast.makeText(
                context,
                "Username and Password cannot be empty",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

class LogInCredentialsValidator{
    fun validate(email: String?, password: String?, context: Context): Boolean{
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            Toast.makeText(context, "Email and Password can't be empty", Toast.LENGTH_SHORT).show()
            return false;
        }
        if(!email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"))){
            Toast.makeText(context, "Invalid Email Format", Toast.LENGTH_SHORT).show()
            return false
        }
        return true ;
    }
}

class TokenValidator{
    fun isTokenValid(context: Context): Boolean{
        val token = LocalStorage(context = context).getToken()
        var isExpired = true
        if(!token.isNullOrEmpty()) {
            // Parse the JWT token string into a JWTClaimsSet object
            val claimsSet: JWTClaimsSet = JWTParser.parse(token).jwtClaimsSet
            // Get the expiration time from the JWT claims set
            val expirationTime: Date? = claimsSet.expirationTime

            // Check if the token is expired or not
            isExpired = expirationTime != null && expirationTime.before(Date())
        }
        return !isExpired
    }
}