package com.abhi41.borutoapp.data.repository


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.abhi41.borutoapp.domain.repository.DataStoreOperations
import com.abhi41.borutoapp.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context) : DataStoreOperations {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = Constants.PREFERENCES_KEY)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { mutablePreferences ->
                val onBoardingState = mutablePreferences[PreferencesKey.onBoardingKey] ?: false
                onBoardingState
            }
    }
}