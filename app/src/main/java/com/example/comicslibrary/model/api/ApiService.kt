package com.example.comicslibrary.model.api

import com.example.comicslibrary.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest

object ApiService {

    private const val BASE_URL = "http://gateway.marvel.com/v1/public/"
    private const val PUBLIC_KEY = BuildConfig.MARVEL_PUBLIC_KEY
    private const val PRIVATE_KEY = BuildConfig.MARVEL_PRIVATE_KEY

    private fun getHash(timestamp: String): String {
        val hashStr = timestamp + PRIVATE_KEY + PUBLIC_KEY
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(hashStr.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }

    private fun getRetrofit(): Retrofit {
        val timestamp = System.currentTimeMillis().toString()
        val hash = getHash(timestamp = timestamp)

        val interceptor = Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("ts", timestamp)
                .addQueryParameter("apikey", PUBLIC_KEY)
                .addQueryParameter("hash", hash)
                .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: MarvelApi = getRetrofit().create(MarvelApi::class.java)

}