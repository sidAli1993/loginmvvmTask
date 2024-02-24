package com.sid_ali_tech.loginapptaskmvvm.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

object Constants {
    const val TAG = "LoginAppMVVM"
    const val FIREBASE_USER = "users"

    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }
}