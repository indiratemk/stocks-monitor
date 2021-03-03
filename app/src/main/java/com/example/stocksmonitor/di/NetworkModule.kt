package com.example.stocksmonitor.di

import com.example.stocksmonitor.utils.Constants
import com.example.stocksmonitor.data.remote.MboumApi
import com.example.stocksmonitor.data.remote.StocksRemoteDataSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideInterceptor() }
    single { provideHttpLoggingInterceptor() }
    single { provideMboumOkHttpClient(get() as Interceptor, get() as HttpLoggingInterceptor) }
    single { provideMboumRetrofit(get()) }
    single { provideMboumApi(get()) }
    single { StocksRemoteDataSource(get()) }
}

fun provideInterceptor(): Interceptor {
    return Interceptor { chain ->
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("apikey", Constants.MBOUM_API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }
}

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}


fun provideMboumOkHttpClient(
    requestInterceptor: Interceptor,
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(requestInterceptor)
        .addNetworkInterceptor(httpLoggingInterceptor)
        .build()
}

fun provideMboumRetrofit(mboumOkHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(mboumOkHttpClient)
        .baseUrl(Constants.MBOUM_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideMboumApi(retrofit: Retrofit): MboumApi {
    return retrofit.create(MboumApi::class.java)
}
