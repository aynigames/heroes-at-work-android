package com.ayni.heroesatwork.application

import android.content.Context

class PreferenceHelper {

    companion object {
        fun savePreference(preferenceKey: String, preferenceValue: String) {
            val preferences = HeroesAtWorkApplication.instance!!.getSharedPreferences(HeroesAtWorkConstants.PREFERENCE_FILE, android.content.Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(preferenceKey, preferenceValue)
            editor.apply()
        }

        fun getStringPreference(preferenceKey: String) : String {
            val preferences = HeroesAtWorkApplication.instance!!.getSharedPreferences(HeroesAtWorkConstants.PREFERENCE_FILE, Context.MODE_PRIVATE)
            return preferences.getString(preferenceKey, "")
        }
    }

}