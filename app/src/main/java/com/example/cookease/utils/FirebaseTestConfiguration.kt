package com.example.cookease.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

object FirebaseTestConfig {
    fun enableOfflinePersistence(db: FirebaseFirestore) {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build()
        db.firestoreSettings = settings
    }

    fun clearCache(db: FirebaseFirestore) {
        db.clearPersistence()
    }
}