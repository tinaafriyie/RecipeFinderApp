# RecipeFinderApp
Easy access to cool recipes


# Recipe Finder App 🍳

A modern Android application for discovering and managing recipes, built with Kotlin, Jetpack Compose, and Firebase.

## Features ✨

- 🔐 **User Authentication** - Firebase Auth with email/password
- 📝 **Recipe Management** - Full CRUD operations on recipes
- 🔍 **Recipe Discovery** - Search recipes from TheMealDB API
- 👤 **Profile Management** - Update profile, change password, upload photos
- ☁️ **Cloud Sync** - Real-time synchronization with Firebase Firestore
- 📴 **Offline Mode** - Continue using the app without internet
- 📊 **Analytics** - Firebase Analytics integration
- 🐛 **Crash Reporting** - Firebase Crashlytics integration
- 📱 **Responsive UI** - Works on phones and tablets

## Tech Stack 🛠️

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material Design 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Backend**: Firebase (Auth, Firestore, Storage, Analytics, Crashlytics)
- **HTTP Client**: Ktor
- **Image Loading**: Coil
- **Navigation**: Jetpack Navigation Compose

## Setup Instructions 📋

1. Clone the repository
2. Open in Android Studio
3. Create Firebase project at https://console.firebase.google.com/
4. Download `google-services.json` and place in `app/` directory
5. Enable Firebase Authentication (Email/Password)
6. Create Firestore Database
7. Enable Firebase Storage
8. Sync Gradle and run

## Architecture 🏗️

```
UI Layer (Compose) → ViewModel → Repository → Data Sources (Firebase/API)
```

## API Used 🌐

- **TheMealDB API**: https://www.themealdb.com/api.php


## Team 👥
Amoako Ernestina Afriyie
Alpatson Cobina Siaw
Kwabena Anokye
Josephine Ama Gyanewah Gyasi

