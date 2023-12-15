package com.example.evvolitm.data

import com.example.evvolitm.network.EvvoliTmApiService
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val evvoliTmApiRepository: EvvoliTmApiRepository
}

private val json = Json { ignoreUnknownKeys = true }

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {

    private val baseUrl = "http://192.168.1.14:8000/"

    // Create a logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Create a OkHttpClient and add the logging interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json { ignoreUnknownKeys = true }
            .asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(client) // Set your custom client
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: EvvoliTmApiService by lazy {
        retrofit.create(EvvoliTmApiService::class.java)
    }

    /**
     * DI implementation for Evvoli TM API repository
     */
    override val evvoliTmApiRepository: EvvoliTmApiRepository by lazy {
        NetworkEvvoliTmApiRepository(retrofitService)
    }

}