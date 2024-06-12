package com.up.meal.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.up.meal.common.Constants
import com.up.meal.data.local.MealDatabase
import com.up.meal.data.local.dao.MealCardDao
import com.up.meal.data.remote.MealApi
import com.up.meal.data.repository.MealRepositoryImpl
import com.up.meal.domain.repository.MealsRepository
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
    fun provideCocktailApi(@ApplicationContext appContext: Context): MealApi {
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
    fun provideMealRepository(api: MealApi, mealCardDao: MealCardDao): MealsRepository {
        return MealRepositoryImpl(api, mealCardDao)
    }

    @Provides
    @Singleton
    fun provideIODispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun provideMealCardDao(database: MealDatabase): MealCardDao {
        return database.mealCardDao()
    }

    @Provides
    @Singleton
    fun provideMealDatabase(@ApplicationContext context: Context): MealDatabase {
        return MealDatabase.getInstance(context)
    }
}