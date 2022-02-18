package com.example.financeapp.api

import android.content.Context
import com.example.financeapp.features.MainActivity
import com.example.financeapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
    context: Context
) : Interceptor {

    var token: String? = context.getSharedPreferences(Constants.USER_PREFERENCES, 0).getString(Constants.TOKEN, "")
    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        val requestBuilder = original.newBuilder()
            .addHeader("Authorization", "Bearer $token")

        val request = requestBuilder.build()
        val response = chain.proceed(request)
        if (response.code == 401) {
            MainActivity.reload()
        }
        return response
    }
}
