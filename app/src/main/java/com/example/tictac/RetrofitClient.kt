package com.example.tictac

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null

    val client: Retrofit?
        get() {
            if (retrofit == null) {
                val httpClient = OkHttpClient.Builder()
                httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val original: Request = chain.request()
                    val requestBuilder: Request.Builder = original.newBuilder()
                    val credentials: String = Credentials.basic(
                        "ybjgznma",
                        "pcgdwVYxf_J2odq9ciLdHwkTAQbXJylt"
                    )
                    requestBuilder.header("Authorization", credentials)
                    val request: Request = requestBuilder.build()
                    chain.proceed(request)
                })
                val client: OkHttpClient = httpClient.build()
                retrofit = Retrofit.Builder()
                    .baseUrl("http://rain.db.elephantsql.com/ybjgznma")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            return retrofit
        }
}