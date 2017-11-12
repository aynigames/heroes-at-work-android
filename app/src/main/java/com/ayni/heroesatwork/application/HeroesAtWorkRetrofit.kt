package com.ayni.heroesatwork.application

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HeroesAtWorkRetrofit {

    companion object {
        val instance: retrofit2.Retrofit by lazy {
            val logging = okhttp3.logging.HttpLoggingInterceptor()

            logging.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY

            val httpClient = okhttp3.OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            httpClient.addInterceptor(okhttp3.Interceptor { chain ->

                val apiToken = PreferenceHelper.getStringPreference(HeroesAtWorkConstants.TOKEN_PREFERENCE_KEY)

                val original = chain.request()

                if (apiToken != "") {

                    val request = original.newBuilder()
                            .header("Authorization", apiToken)
                            .method(original.method(), original.body())
                            .build()
                    return@Interceptor chain.proceed(request)
                }

                chain.proceed(original)
            })
            httpClient.connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            httpClient.readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)

            retrofit2.Retrofit.Builder()
                    .baseUrl(HeroesAtWorkConstants.BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }


    }
}