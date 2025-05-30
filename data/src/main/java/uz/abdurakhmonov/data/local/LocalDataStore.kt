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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import uz.abdurakhmonov.data.remote.local.HistoryDate
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
            preferences[DATE] = date
        }
    }

    suspend fun setFlags(flags: List<HistoryDate>) = withContext(Dispatchers.IO) {
        val flag = json.toJson(flags)
        context.dataStore.edit { preferences ->
            preferences[FLAGS] = flag
        }
    }

    fun getFlags(): Flow<List<HistoryDate>> = flow {
        val preferences = context.dataStore.data.firstOrNull()
        val flagJson = preferences?.get(FLAGS) ?: "[]"
        val type = object : TypeToken<List<HistoryDate>>() {}.type
        emit(json.fromJson(flagJson, type) ?: emptyList())
    }

    suspend fun getDate(): Long = withContext(Dispatchers.IO) {
        val preferences = context.dataStore.data.firstOrNull()
        preferences?.get(DATE)?.toLong()?:0L
    }

    suspend fun setState(state: String) = withContext(Dispatchers.IO) {
        context.dataStore.edit { preferences ->
            preferences[STATE] = state
        }
    }

    suspend fun getState(): String = withContext(Dispatchers.IO) {
        val preferences = context.dataStore.data.firstOrNull()
        preferences?.get(STATE) ?: ""
    }

    suspend fun setTimer(time: Long) = withContext(Dispatchers.IO) {
        context.dataStore.edit { preferences ->
            preferences[TIME] = time.toString()
        }
    }

    suspend fun getTimer(): Long = withContext(Dispatchers.IO) {
        val preferences = context.dataStore.data.firstOrNull()
        preferences?.get(TIME)?.toLong() ?: 0L
    }

    suspend fun setCounter(counter: Int) = withContext(Dispatchers.IO) {
        context.dataStore.edit { preferences ->
            preferences[COUNTER] = counter
        }
    }

    suspend fun getCounter(): Int = withContext(Dispatchers.IO) {
        val preferences = context.dataStore.data.firstOrNull()
        preferences?.get(COUNTER) ?: 0
    }
}