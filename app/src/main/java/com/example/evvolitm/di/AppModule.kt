package com.example.evvolitm.di

import android.app.Application
import androidx.room.Room
import com.example.evvolitm.data.local.EvvoliTmDatabase
import com.example.evvolitm.data.local.cart.CartItemDao
import com.example.evvolitm.data.remote.EvvoliTmApi
import com.example.evvolitm.data.remote.EvvoliTmApi.Companion.BASE_URL
import com.example.evvolitm.data.repository.CartRepositoryImpl
import com.example.evvolitm.domain.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val baseUrl = "http://192.168.1.14:8000/"

    // Create a logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Create a OkHttpClient and add the logging interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideEvvoliTmApi() : EvvoliTmApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(EvvoliTmApi::class.java)
    }


    @Provides
    @Singleton
    fun provideEvvoliTmDatabase(app: Application): EvvoliTmDatabase {
        return Room.databaseBuilder(
            app,
            EvvoliTmDatabase::class.java,
            "evvoli_tm_db.db"
        ).build()
    }

}