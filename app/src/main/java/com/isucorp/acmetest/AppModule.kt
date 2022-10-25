package com.isucorp.acmetest

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.isucorp.acmetest.data.local.database.AppDatabase
import com.isucorp.acmetest.data.local.database.WorkTicketLocalDatasourceImpl
import com.isucorp.acmetest.data.local.datasource.LoginCredentialsLocalDatasource
import com.isucorp.acmetest.data.local.datasource.WorkTicketLocalDatasource
import com.isucorp.acmetest.data.local.datasource.DataObjectStorage
import com.isucorp.acmetest.data.local.datasource.LoginCredentialsLocalDatasourceImpl
import com.isucorp.acmetest.data.local.model.LoginCredentials
import com.isucorp.acmetest.data.local.repository.LoginRepositoryImpl
import com.isucorp.acmetest.data.local.repository.WorkTicketRepositoryImpl
import com.isucorp.acmetest.data.remote.GoogleMapsApiService
import com.isucorp.acmetest.data.remote.datasource.CoordinatesRemoteDataSource
import com.isucorp.acmetest.data.remote.datasource.CoordinatesRemoteDataSourceImpl
import com.isucorp.acmetest.data.remote.repository.CoordinatesRepositoryImpl
import com.isucorp.acmetest.domain.repository.CoordinatesRepository
import com.isucorp.acmetest.domain.repository.LoginRepository
import com.isucorp.acmetest.domain.repository.WorkTicketRepository
import com.isucorp.acmetest.domain.usecase.*
import com.isucorp.acmetest.presentation.viewmodel.DashBoardViewModel
import com.isucorp.acmetest.presentation.viewmodel.LoginViewModel
import com.isucorp.acmetest.presentation.viewmodel.MainActivityViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val Context.datastore: DataStore<Preferences> by preferencesDataStore(AppConstants.APP_PREFERENCES)

val baseModule = module {
    single { Gson() }
    single { OkHttpClient() }
}

val loginModule = module {

    includes(baseModule)

    single {
        DataObjectStorage<LoginCredentials>(
            get(), object : TypeToken<LoginCredentials>() {}.type, androidContext().datastore,
            stringPreferencesKey((AppConstants.LOGIN_CREDENTIALS_KEY))
        )
    }

    single<LoginCredentialsLocalDatasource> {
        LoginCredentialsLocalDatasourceImpl(get())
    }

    single<LoginRepository> {
        LoginRepositoryImpl(get())
    }

    single {
        LoginUseCase(get())
    }

    viewModel { LoginViewModel(get()) }
}

val workTicketModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            AppConstants.APP_DATABASE_NAME
        ).build()
    }

    single {
        get<AppDatabase>().workTicketDao()
    }

    single<WorkTicketLocalDatasource> {
        WorkTicketLocalDatasourceImpl(get())
    }

    single<WorkTicketRepository> {
        WorkTicketRepositoryImpl(get())
    }

    single {
        GetListOfWorkTicketsUseCase(get())
    }

    single {
        GetDueTicketsUseCase(get())
    }

    single {
        InsertWorkTicketUseCase(get())
    }

    single {
        UpdateWorkTicketUseCase(get())
    }

    single {
        GetWorkTicketUseCase(get())
    }

    single {
        DeleteWorkTicketUseCase(get())
    }

    single {
        WorkTicketUseCase(get(), get(), get(), get(), get(), get())
    }

    viewModel { DashBoardViewModel(get()) }

    viewModel { MainActivityViewModel(get(),get()) }

    single<GoogleMapsApiService> {
        Retrofit.Builder()
            .baseUrl(AppConstants.GOOGLE_API_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GoogleMapsApiService::class.java)
    }

    single<CoordinatesRemoteDataSource> {
        CoordinatesRemoteDataSourceImpl(get())
    }

    single<CoordinatesRepository> {
        CoordinatesRepositoryImpl(get())
    }

    single {
        GeocodingUseCase(get())
    }


}
