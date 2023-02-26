package com.example.skillathon.utils

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.example.skillathon.models.ScholarshipItem
import com.example.skillathon.utils.volley.SignUpVolley

class ScholarshipListUtil {
    fun signUp(scholarshipList: List<ScholarshipItem>,
               navController: NavHostController,
               context: Context,
               pleaseWaitDialogDisplay: MutableState<Boolean>
    ) {
        pleaseWaitDialogDisplay.value = true
    }
}