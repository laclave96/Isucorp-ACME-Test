package com.isucorp.acmetest.data.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.isucorp.acmetest.AppConstants
import com.isucorp.acmetest.R
import com.isucorp.acmetest.utils.Resource
import kotlinx.coroutines.flow.*
import java.lang.reflect.Type

class DataObjectStorage<T> constructor(
    private val gson: Gson,
    private val type: Type,
    private val dataStore: DataStore<Preferences>,
    private val preferenceKey: Preferences.Key<String>
) {

    fun saveData(data: T): Flow<Resource<T>> = flow {
        try {
            dataStore.edit {
                val jsonString = gson.toJson(data, type)
                it[preferenceKey] = jsonString
            }
        } catch (e: Exception) {
            val resId = R.string.save_login_credentials_error
            emit(Resource.Error<T>(resId = resId))
        }
        emit(Resource.Success(data))
    }

    fun getData(): Flow<Resource<T>> = flow {
        dataStore.data.map { preferences ->
            val jsonString = preferences[preferenceKey] ?: AppConstants.EMPTY_JSON_STRING
            val elements = gson.fromJson<T>(jsonString, type)
            elements
        }.catch {
            val resId = R.string.fetch_login_credentials_error
            emit(Resource.Error<T>(resId = resId))
        }.collect { emit(Resource.Success(it)) }

    }

    fun clear(): Flow<Resource<T>> = flow {
        try {
            dataStore.edit { it.clear() }
        } catch (e: Exception) {
            val resId = R.string.delete_login_credentials_error
            emit(Resource.Error<T>(resId = resId))
        }
        emit(Resource.Success())
    }

}