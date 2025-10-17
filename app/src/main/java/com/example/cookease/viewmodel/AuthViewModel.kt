package com.example.cookease.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookease.data.model.User
import com.example.cookease.data.repository.AuthRepository
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()
    private val analytics = Firebase.analytics

    var user = mutableStateOf<User?>(null)
        private set

    var isLoading = mutableStateOf(false)
        private set

    var error = mutableStateOf<String?>(null)
        private set

    init {
        user.value = repository.currentUser
    }

    fun signIn(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            repository.signIn(email, password)
                .onSuccess {
                    user.value = it
                    analytics.logEvent(FirebaseAnalytics.Event.LOGIN, null)
                    onSuccess()
                }
                .onFailure {
                    error.value = it.message
                }

            isLoading.value = false
        }
    }

    fun signUp(email: String, password: String, displayName: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            repository.signUp(email, password, displayName)
                .onSuccess {
                    user.value = it
                    analytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, null)
                    onSuccess()
                }
                .onFailure {
                    error.value = it.message
                }

            isLoading.value = false
        }
    }

    fun signOut() {
        repository.signOut()
        user.value = null
    }

    fun updateProfile(displayName: String, photoUri: Uri?, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            repository.updateProfile(displayName, photoUri)
                .onSuccess {
                    user.value = repository.currentUser
                    onSuccess()
                }
                .onFailure {
                    error.value = it.message
                }

            isLoading.value = false
        }
    }

    fun changePassword(newPassword: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            repository.changePassword(newPassword)
                .onSuccess { onSuccess() }
                .onFailure { error.value = it.message }

            isLoading.value = false
        }
    }
}