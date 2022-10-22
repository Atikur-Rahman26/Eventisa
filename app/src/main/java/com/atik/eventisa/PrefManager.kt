package com.atik.eventisa

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context:Context?) {
    val MODE=0

    private val PREF_EMAIL="SharedPreferences"
    private val Is_Login="is_login"


    val pref:SharedPreferences?=context?.getSharedPreferences(PREF_EMAIL,MODE)
    val editor:SharedPreferences.Editor?=pref?.edit()


}