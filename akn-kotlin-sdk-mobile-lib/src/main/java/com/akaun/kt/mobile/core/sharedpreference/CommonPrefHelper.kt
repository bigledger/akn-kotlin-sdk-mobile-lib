package com.akaun.kt.mobile.core.sharedpreference

import android.content.Context
import android.content.SharedPreferences

object CommonPrefHelper {
    const val LOGIN_PREF_NAME = "userLoginPreferences"
    const val COMMON_PREF_NAME = "commonAppPreferences"

    lateinit var appContext: Context


    fun getPrefs(prefName: String): SharedPreferences =
        appContext.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    fun getPrefs(context: Context, prefName: String): SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    inline fun SharedPreferences.operation(editor: (SharedPreferences.Editor) -> Unit) {
        val edit = edit()
        editor(edit)
        edit.apply()
    }
}