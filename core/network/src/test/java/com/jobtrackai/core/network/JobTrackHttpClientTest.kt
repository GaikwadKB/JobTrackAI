package com.jobtrackai.core.network

import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class JobTrackHttpClientTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var httpClient: JobTrackHttpClient
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        httpClient = JobTrackHttpClient(OkHttpClient(), json)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get returns success when server returns 200`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{"id":"1", "name":"Test"}""")
        mockWebServer.enqueue(mockResponse)

        val result: ApiResult<TestData> = httpClient.get(mockWebServer.url("/test").toString())

        assertTrue(result is ApiResult.Success)
        assertEquals("1", (result as ApiResult.Success).data.id)
        assertEquals("Test", result.data.name)
    }

    @Test
    fun `get returns failure when server returns 404`() = runTest {
        val mockResponse = MockResponse().setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        val result: ApiResult<TestData> = httpClient.get(mockWebServer.url("/test").toString())

        assertTrue(result is ApiResult.Failure)
        assertEquals(404, (result as ApiResult.Failure).httpCode)
    }

    @Serializable
    data class TestData(val id: String, val name: String)
}
