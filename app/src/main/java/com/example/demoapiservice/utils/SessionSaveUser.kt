package com.example.demoapiservice.utils
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.service.autofill.UserData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.Exception

class SessionSaveUser private constructor(context: Context) {
    var context: Context? = context
    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    fun putSharedString(key: String?, value: String?) {
        if (!isValid(sharedPreferences)) sharedPreferences = context!!.getSharedPreferences(
            PREFERENCES, Context.MODE_PRIVATE
        )
        if (!isValid(editor)) editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getSharedString(key: String?, defValue: String?): String? {
        if (!isValid(sharedPreferences)) sharedPreferences = context!!.getSharedPreferences(
            PREFERENCES, Context.MODE_PRIVATE
        )
        if (!isValid(editor)) editor = sharedPreferences.edit()
        return sharedPreferences.getString(key, defValue)
    }

    fun getSharedBoolean(key: String?, defValue: Boolean): Boolean {
        if (!isValid(sharedPreferences)) sharedPreferences = context!!.getSharedPreferences(
            PREFERENCES, Context.MODE_PRIVATE
        )
        if (!isValid(editor)) editor = sharedPreferences.edit()
        return sharedPreferences.getBoolean(key, defValue)
    }

    fun putSharedBoolean(key: String?, value: Boolean) {
        if (!isValid(sharedPreferences)) sharedPreferences = context!!.getSharedPreferences(
            PREFERENCES, Context.MODE_PRIVATE
        )
        if (!isValid(editor)) editor = sharedPreferences.edit()
        editor.putBoolean(key, value).apply()
    }

    private fun isValid(text: Any?): Boolean {
        return text != null
    }

    fun saveUser(root: UserData?) {
        try {
            val gson = Gson()
            val json = gson.toJson(root)
            putSharedString(USER, json)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val user: UserData?
        get() {
            var loginRoot: UserData? = null
            try {
                val jsonString = getSharedString(USER, "")
                val gson = GsonBuilder().setPrettyPrinting().create()
                loginRoot = gson.fromJson(jsonString, UserData::class.java)
            } catch (e: Exception) {
                e.message
            }
            return loginRoot
        }

    fun logout() {
        try {
            val json: String? = null
            putSharedString(USER, json)
            clearSharedAll()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun clearSharedAll() {
        if (!isValid(editor)) {
            if (!isValid(sharedPreferences)) sharedPreferences = context!!.getSharedPreferences(
                PREFERENCES, Context.MODE_PRIVATE
            )
            editor = sharedPreferences.edit()
        }
        editor.clear().apply()
    }

    companion object {
        const val PREFERENCES = "user_preference"
        const val USER = "user"
        private var instance: SessionSaveUser? = null
        fun getInstance(context: Context): SessionSaveUser? {
            if (instance == null) {
                instance = SessionSaveUser(context)
            } else {
                instance!!.context = null
                instance!!.context = context
            }
            return instance
        }

        // Delete Data's from SharedPreferences
        fun deleteSession(context: Context) {
            val editor = context.getSharedPreferences(PREFERENCES, Activity.MODE_PRIVATE).edit()
            editor.clear()
            editor.apply()
        }
    }

    init {
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }
}
