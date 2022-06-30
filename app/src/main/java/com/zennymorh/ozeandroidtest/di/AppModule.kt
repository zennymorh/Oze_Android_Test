package com.zennymorh.ozeandroidtest.di

import android.content.Context
import com.zennymorh.ozeandroidtest.api.ApiService
import com.zennymorh.ozeandroidtest.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBaseApplication(
        @ApplicationContext app: Context
    ): MyApplication{
        return app as MyApplication
    }
}
