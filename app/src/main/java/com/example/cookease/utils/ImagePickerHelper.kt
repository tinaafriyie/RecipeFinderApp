package com.example.cookease.utils

import android.content.Context
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

object ImagePickerHelper {
    @Composable
    fun rememberImagePicker(
        onImageSelected: (Uri?) -> Unit
    ): ManagedActivityResultLauncher<String, Uri?> {
        return rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            onImageSelected(uri)
        }
    }
}