package com.example.loginapp.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun preferenceDataStore(name: Any) {}

val Context.dataStore by preferenceDataStore(name: "settings")

        object PrefsKeys{
            val FIRST_RUN = booleanPreferenceKey(name:"first_run")
        }
class PrefsDataStore(private val context: Context){
    val isFirstRun: Flow<Boolean> = context.dataStore.data.map {
        prefs -> prefs[PrefsKeys.FIRST_RUN] ?: true
    }
    suspend fun setFirstRunFalse(){
        context.dataStore.edit {
            prefs -> prefs[PrefsKeys.FIRST_RUN] = false
        }
    }
}

