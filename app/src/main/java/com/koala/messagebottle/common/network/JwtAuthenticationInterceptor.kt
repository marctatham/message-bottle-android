package com.koala.messagebottle.common.network

import com.koala.messagebottle.common.authentication.data.jwt.IJwtTokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

private const val HEADER_AUTH = "Authorization"

@Singleton
class JwtAuthenticationInterceptor @Inject constructor(
    private val jwtTokenProvider: IJwtTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.url(original.url)

        // TODO: let's clean this up a bit
        // for now it's sufficient to not get it if it's a call to the user endpoint
        if (original.url.pathSegments.last() != "user") {
            jwtTokenProvider.get()?.let {
                requestBuilder.addHeader(HEADER_AUTH, "Bearer ${it.token}")
            }
        }

        val request = requestBuilder.build()
        return@runBlocking chain.proceed(request)
    }
}