package com.jobtrackai.feature.profile.presentation.details

import app.cash.turbine.test
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jobtrackai.core.common.model.UserProfile
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.feature.profile.domain.usecase.GetProfileUseCase
import com.jobtrackai.feature.profile.domain.usecase.UpdateProfileUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private val getProfileUseCase: GetProfileUseCase = mockk()
    private val updateProfileUseCase: UpdateProfileUseCase = mockk()
    private val firebaseAuth: FirebaseAuth = mockk()
    private val firebaseUser: FirebaseUser = mockk()
    
    private lateinit var viewModel: ProfileViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { firebaseAuth.currentUser } returns firebaseUser
        every { firebaseUser.uid } returns "user123"
        
        // Default mock for profile fetch
        every { getProfileUseCase("user123") } returns flowOf(DomainResult.Success(null))
        
        viewModel = ProfileViewModel(getProfileUseCase, updateProfileUseCase, firebaseAuth)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state fetches profile and becomes Empty if null`() = runTest {
        viewModel.profileState.test {
            // Initial loading emitted by flow
            assertTrue(awaitItem() is UiState.Loading)
            // Empty state from our mock
            assertTrue(awaitItem() is UiState.Empty)
        }
    }

    @Test
    fun `startEditing updates editState`() = runTest {
        val profile = UserProfile(userId = "user123", name = "Test User")
        viewModel.startEditing(profile)
        assertEquals(profile, viewModel.editState.value)
    }

    @Test
    fun `saveProfile calls use case and clears editState on success`() = runTest {
        val profile = UserProfile(userId = "user123", name = "Test User")
        coEvery { updateProfileUseCase(profile) } returns DomainResult.Success(Unit)
        
        viewModel.startEditing(profile)
        viewModel.saveProfile()
        
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertEquals(null, viewModel.editState.value)
    }
}
