package com.azamovhudstc.beforixinc.utils

import android.content.Context
import android.content.SharedPreferences

object MyMovie {
    private const val NAME="contact"
    private const val MODE=Context.MODE_PRIVATE
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context){
        sharedPreferences =context.getSharedPreferences(NAME, MODE)

    }
    private inline fun SharedPreferences.edit(operation:(SharedPreferences.Editor)->Unit){
        var editor=edit()
        operation(editor)
        editor.apply()
    }
    var text:String?
        get() = sharedPreferences.getString("key1","")
        set(value) = sharedPreferences.edit {
            if (value!=""){
                it.putString("key1",value)
            }
        }
    var user: String?
        get() = sharedPreferences.getString("key1","")
        set(value) = sharedPreferences.edit {
            if (value!=""){
                it.putString("key1",value)
            }
        }
    var items: String?
        get() = sharedPreferences.getString("key1","")
        set(value) = sharedPreferences.edit {
            if (value!=""){
                it.putString("key1",value)
            }
        }
}

