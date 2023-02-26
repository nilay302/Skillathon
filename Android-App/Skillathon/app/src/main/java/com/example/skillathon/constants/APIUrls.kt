package com.example.skillathon.constants

object APIUrls {
    private const val BASE_URL = "https://bap-production.up.railway.app"
    const val SIGN_UP_URL = "${BASE_URL}/user/profile"
    const val LOG_IN_URL = "${BASE_URL}/user/profile/login"
    const val SCHOLARSHIP_LIST_URL = "${BASE_URL}/scholarship"
    const val APPLY_SCHOLARSHIP_URL = "${BASE_URL}/apply/scholarship"
}