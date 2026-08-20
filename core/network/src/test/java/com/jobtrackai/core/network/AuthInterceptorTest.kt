package com.jobtrackai.core.network

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.tasks.await
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AuthInterceptorTest {

    private lateinit var mockWebServer: MockWebServer
    private val firebaseAuth: FirebaseAuth = mockk()
    private val firebaseUser: FirebaseUser = mockk()
    private val tokenResult: GetTokenResult = mockk()
    private val tokenTask: Task<GetTokenResult> = mockk()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `interceptor adds auth header when user is logged in`() {
        every { firebaseAuth.currentUser } returns firebaseUser
        every { firebaseUser.getIdToken(false) } returns tokenTask
        every { tokenResult.token } returns "mock-token"
        coEvery { tokenTask.await() } returns tokenResult

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(firebaseAuth))
            .build()

        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        client.newCall(Request.Builder().url(mockWebServer.url("/")).build()).execute()

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("Bearer mock-token", recordedRequest.getHeader("Authorization"))
    }

    @Test
    fun `interceptor does not add auth header when user is logged out`() {
        every { firebaseAuth.currentUser } returns null

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(firebaseAuth))
            .build()

        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        client.newCall(Request.Builder().url(mockWebServer.url("/")).build()).execute()

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(null, recordedRequest.getHeader("Authorization"))
    }
}
