package com.example.final_case.di

import android.content.Context
import com.example.final_case.common.Constants
import com.example.final_case.data.local.FinalCaseDatabase
import com.example.final_case.data.local.dao.MealCardDao
import com.example.final_case.data.remote.MealApi
import com.example.final_case.data.repository.MealRepositoryImpl
import com.example.final_case.domain.repository.MealRepository
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMealApi(@ApplicationContext appContext: Context): MealApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(appContext)
                    .collector(ChuckerCollector(appContext))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMealRepository(api: MealApi, mealCardDao: MealCardDao): MealRepository {
        return MealRepositoryImpl(api, mealCardDao)
    }

    @Provides
    @Singleton
    fun provideIODispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun provideMealCardDao(database: FinalCaseDatabase): MealCardDao {
        return database.mealCardDao()
    }

    @Provides
    @Singleton
    fun provideFinalCaseDatabase(@ApplicationContext context: Context): FinalCaseDatabase {
        return FinalCaseDatabase.getInstance(context)
    }
}