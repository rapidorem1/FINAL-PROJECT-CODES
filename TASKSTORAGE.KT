TASKSTORAGE.KT
package com.example.pawminder

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TaskStorage {

    private const val PREF_NAME = "pawminder_prefs"
    private const val KEY_TASKS = "tasks"

    private val gson = Gson()

    fun loadTasks(context: Context): MutableList<Task> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_TASKS, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<Task>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveTasks(context: Context, tasks: List<Task>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = gson.toJson(tasks)
        prefs.edit().putString(KEY_TASKS, json).apply()
    }
}
