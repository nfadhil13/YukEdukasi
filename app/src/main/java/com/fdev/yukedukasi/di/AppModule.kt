package com.fdev.yukedukasi.di

import android.content.Context
import android.content.SharedPreferences
import com.fdev.yukedukasi.business.data.network.NetworkConstants
import com.fdev.yukedukasi.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideSharedPreferences(
            @ApplicationContext context: Context
    ): SharedPreferences {
        return context
                .getSharedPreferences(
                        Constants.APP_PREFENCES,
                        Context.MODE_PRIVATE
                )
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesEditor(
            sharedPreferences: SharedPreferences
    ): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
                .create()
    }


    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideOkHttpCliendHeader(): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS )
//
//        val bodyIntreceptor = HttpLoggingInterceptor()
//        bodyIntreceptor.setLevel(HttpLoggingInterceptor.Level.BODY )

        return OkHttpClient.Builder().apply {
            addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("X-Api-Key", "A14135FAFF71C35F361EA0F2C20B9DBC")
                        return@Interceptor chain.proceed(builder.build())
                    }
            )
//            addNetworkInterceptor(interceptor)
//            addNetworkInterceptor(bodyIntreceptor)
        }.build()
    }


}