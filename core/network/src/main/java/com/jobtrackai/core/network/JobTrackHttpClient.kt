package com.jobtrackai.core.network

import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * A reusable wrapper around [OkHttpClient] and [Json] (Section 26).
 *
 * This provides high-level methods for common HTTP operations while
 * automatically handling serialization and error mapping. This replaces
 * Retrofit for this project to demonstrate low-level networking expertise.
 */
@Singleton
class JobTrackHttpClient @Inject constructor(
    private val okHttpClient: OkHttpClient,
    @PublishedApi internal val json: Json
) {

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    /**
     * Executes a GET request and parses the response into [T].
     */
    suspend inline fun <reified T> get(
        url: String,
        headers: Map<String, String> = emptyMap()
    ): ApiResult<T> = safeRequest(url, "GET", null, headers, typeOf<T>())

    /**
     * Executes a POST request with a JSON body.
     */
    suspend inline fun <reified T, reified B : Any> post(
        url: String,
        body: B,
        headers: Map<String, String> = emptyMap()
    ): ApiResult<T> {
        val jsonBody = json.encodeToString(serializer<B>(), body)
        return safeRequest(url, "POST", jsonBody, headers, typeOf<T>())
    }

    /**
     * The internal core of the client that executes the OkHttp call.
     */
    @PublishedApi
    internal fun <T> safeRequest(
        url: String,
        method: String,
        jsonBody: String?,
        headers: Map<String, String>,
        responseType: KType
    ): ApiResult<T> = try {
        val requestBuilder = Request.Builder()
            .url(url)
            .method(method, jsonBody?.toRequestBody(jsonMediaType))

        headers.forEach { (name, value) ->
            requestBuilder.addHeader(name, value)
        }

        // We use execute() here because this function is called from a suspend
        // context (inline get/post) which is expected to be running on a
        // background dispatcher (Rule 4).
        val response = okHttpClient.newCall(requestBuilder.build()).execute()
        val bodyString = response.body?.string()

        if (response.isSuccessful) {
            if (bodyString == null) {
                ApiResult.Failure(response.code, "Empty response body", null)
            } else {
                @Suppress("UNCHECKED_CAST")
                val data = json.decodeFromString(json.serializersModule.serializer(responseType), bodyString) as T
                ApiResult.Success(data, response.code)
            }
        } else {
            ApiResult.Failure(response.code, bodyString, null)
        }
    } catch (e: Exception) {
        ApiResult.Failure(null, null, e)
    }
}
