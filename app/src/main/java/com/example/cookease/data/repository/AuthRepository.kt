package com.example.cookease.data.repository

import android.net.Uri
import com.example.cookease.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()

    val currentUser get() = auth.currentUser?.let {
        User(
            uid = it.uid,
            email = it.email ?: "",
            displayName = it.displayName ?: "",
            photoUrl = it.photoUrl?.toString() ?: ""
        )
    }

    suspend fun signIn(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user?.let {
                Result.success(User(
                    uid = it.uid,
                    email = it.email ?: "",
                    displayName = it.displayName ?: "",
                    photoUrl = it.photoUrl?.toString() ?: ""
                ))
            } ?: Result.failure(Exception("Sign in failed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(email: String, password: String, displayName: String): Result<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let { user ->
                user.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .build()
                ).await()

                Result.success(User(
                    uid = user.uid,
                    email = user.email ?: "",
                    displayName = displayName,
                    photoUrl = ""
                ))
            } ?: Result.failure(Exception("Sign up failed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    suspend fun updateProfile(displayName: String, photoUri: Uri?): Result<Unit> {
        return try {
            val user = auth.currentUser ?: return Result.failure(Exception("No user logged in"))

            var photoUrl: String? = null
            if (photoUri != null) {
                val ref = storage.reference.child("profile_images/${user.uid}")
                ref.putFile(photoUri).await()
                photoUrl = ref.downloadUrl.await().toString()
            }

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .apply { if (photoUrl != null) setPhotoUri(Uri.parse(photoUrl)) }
                .build()

            user.updateProfile(profileUpdates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun changePassword(newPassword: String): Result<Unit> {
        return try {
            val user = auth.currentUser ?: return Result.failure(Exception("No user logged in"))
            user.updatePassword(newPassword).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}