package com.example.stocksmonitor.di

import com.example.stocksmonitor.data.remote.FinnhubApi
import com.example.stocksmonitor.data.remote.MboumApi
import com.example.stocksmonitor.data.remote.StocksRemoteDataSource
import com.example.stocksmonitor.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single(named("MboumInterceptor")) { provideMboumInterceptor() }

    single(named("FunnhubInterceptor")) { provideFinnhubInterceptor() }

    single(named("MboumOkHttpClient")) {
        provideMboumOkHttpClient(get(named("MboumInterceptor")) as Interceptor)
    }

    single(named("FinnhubOkHttpClient")) {
        provideFinnhubOkHttpClient(get(named("FunnhubInterceptor")) as Interceptor)
    }

    single(named("MboumRetrofit")) {
        provideMboumRetrofit(get(named("MboumOkHttpClient")))
    }

    single(named("FinnhubRetrofit")) {
        provideFinnhubRetrofit(get(named("FinnhubOkHttpClient")))
    }

    single { provideMboumApi(get(named("MboumRetrofit"))) }

    single { provideFinnhubApi(get(named("FinnhubRetrofit"))) }

    single { StocksRemoteDataSource(get() as MboumApi, get() as FinnhubApi) }
}

fun provideMboumInterceptor(): Interceptor {
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

fun provideFinnhubInterceptor(): Interceptor {
    return Interceptor { chain ->
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("token", Constants.FINNHUB_API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }
}

fun provideMboumOkHttpClient(
    requestInterceptor: Interceptor
): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(requestInterceptor)
        .build()
}

fun provideFinnhubOkHttpClient(
    requestInterceptor: Interceptor
): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(requestInterceptor)
        .build()
}

fun provideMboumRetrofit(mboumOkHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(mboumOkHttpClient)
        .baseUrl(Constants.MBOUM_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideFinnhubRetrofit(mboumOkHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(mboumOkHttpClient)
        .baseUrl(Constants.FINNHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideMboumApi(retrofit: Retrofit): MboumApi {
    return retrofit.create(MboumApi::class.java)
}

fun provideFinnhubApi(retrofit: Retrofit): FinnhubApi {
    return retrofit.create(FinnhubApi::class.java)
}