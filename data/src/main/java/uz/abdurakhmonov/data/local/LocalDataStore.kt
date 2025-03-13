package uz.abdurakhmonov.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import uz.abdurakhmonov.data.remote.HistoryDate
import javax.inject.Inject

internal class LocalDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "local_store")
    private val json = Gson()

    companion object {
        val DATE = longPreferencesKey("date")
        val FLAGS = stringPreferencesKey("flags")
        val STATE = stringPreferencesKey("state")
        val TIME = stringPreferencesKey("time")
        val COUNTER = intPreferencesKey("counter")

    }

    suspend fun setDate(date: Long) = withContext(Dispatchers.IO) {
        context.dataStore.edit { preferences ->
            preferences[DATE] = date?:0L
        }
    }

    suspend fun setFlags(flags: List<HistoryDate>) = withContext(Dispatchers.IO) {
        val flag = json.toJson(flags)
        context.dataStore.edit { preferences ->
            preferences[FLAGS] = flag
        }
    }

    suspend fun getFlags(): List<HistoryDate> = withContext(Dispatchers.IO) {
        val preferences = context.dataStore.data.first()
        val flagJson = preferences[FLAGS] ?: "[]"
        val type = object : TypeToken<List<HistoryDate>>() {}.type
        Gson().fromJson(flagJson, type) ?: emptyList()
    }

    suspend fun getDate(): Long = withContext(Dispatchers.IO) {
        val preferences = context.dataStore.data.first()
        preferences[DATE] ?: 0L
    }

    suspend fun setState(state: String) = withContext(Dispatchers.IO) {
        context.dataStore.edit { preferences ->
            preferences[STATE] = state
        }
    }

    suspend fun getState(): String = withContext(Dispatchers.IO) {
        val preferences = context.dataStore.data.first()
        preferences[STATE] ?: ""
    }

    suspend fun setTimer(time: Long) = withContext(Dispatchers.IO) {
        context.dataStore.edit { preferences ->
            preferences[TIME] = time.toString()
        }
    }

    suspend fun getTimer(): Long = withContext(Dispatchers.IO) {
        val preferences = context.dataStore.data.first()
        preferences[TIME]?.toLong() ?: 0L
    }

    suspend fun setCounter(counter: Int) = withContext(Dispatchers.IO) {
        context.dataStore.edit { preferences ->
            preferences[COUNTER] = counter
        }
    }

    suspend fun getCounter(): Int = withContext(Dispatchers.IO) {
        val preferences = context.dataStore.data.first()
        preferences[COUNTER] ?: 0
    }
}