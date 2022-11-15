package algorithm.master.currencyconverterpro.di

import algorithm.master.currencyconverterpro.BuildConfig
import algorithm.master.currencyconverterpro.data.remote.api.CurrencyService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by promasterguru on 15/11/2022.
 */
@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideOkHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder().header(
                    "apikey", BuildConfig.API_KEY
                ).build()
            )
        }
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: Interceptor, loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder().apply {
            addInterceptor(headerInterceptor)
            addInterceptor(loggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.apilayer.com/exchangerates_data/")
            .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
    }
}
