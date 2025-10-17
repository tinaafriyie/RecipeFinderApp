package com.example.cookease.ui.theme
//
//import android.app.Activity
//import android.os.Build
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.darkColorScheme
//import androidx.compose.material3.dynamicDarkColorScheme
//import androidx.compose.material3.dynamicLightColorScheme
//import androidx.compose.material3.lightColorScheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.sp
//
//import androidx.compose.material3.Typography
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.sp
//
//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)
//
//@Composable
//fun CookEaseTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )
//
//    val Typography = Typography(
//        displayLarge = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Normal,
//            fontSize = 57.sp,
//            lineHeight = 64.sp,
//            letterSpacing = 0.sp
//        ),
//        displayMedium = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Normal,
//            fontSize = 45.sp,
//            lineHeight = 52.sp,
//            letterSpacing = 0.sp
//        ),
//        displaySmall = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Normal,
//            fontSize = 36.sp,
//            lineHeight = 44.sp,
//            letterSpacing = 0.sp
//        ),
//        headlineLarge = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Bold,
//            fontSize = 32.sp,
//            lineHeight = 40.sp,
//            letterSpacing = 0.sp
//        ),
//        headlineMedium = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Bold,
//            fontSize = 28.sp,
//            lineHeight = 36.sp,
//            letterSpacing = 0.sp
//        ),
//        headlineSmall = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Bold,
//            fontSize = 24.sp,
//            lineHeight = 32.sp,
//            letterSpacing = 0.sp
//        ),
//        titleLarge = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Bold,
//            fontSize = 22.sp,
//            lineHeight = 28.sp,
//            letterSpacing = 0.sp
//        ),
//        titleMedium = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Bold,
//            fontSize = 16.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.15.sp
//        ),
//        titleSmall = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Bold,
//            fontSize = 14.sp,
//            lineHeight = 20.sp,
//            letterSpacing = 0.1.sp
//        ),
//        bodyLarge = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.5.sp
//        ),
//        bodyMedium = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Normal,
//            fontSize = 14.sp,
//            lineHeight = 20.sp,
//            letterSpacing = 0.25.sp
//        ),
//        bodySmall = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Normal,
//            fontSize = 12.sp,
//            lineHeight = 16.sp,
//            letterSpacing = 0.4.sp
//        ),
//        labelLarge = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Medium,
//            fontSize = 14.sp,
//            lineHeight = 20.sp,
//            letterSpacing = 0.1.sp
//        ),
//        labelMedium = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Medium,
//            fontSize = 12.sp,
//            lineHeight = 16.sp,
//            letterSpacing = 0.5.sp
//        ),
//        labelSmall = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Medium,
//            fontSize = 11.sp,
//            lineHeight = 16.sp,
//            letterSpacing = 0.5.sp
//        )
//    )
//
//}

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6750A4),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),
    secondary = Color(0xFF625B71),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF1D192B),
    tertiary = Color(0xFF7D5260),
    onTertiary = Color.White,
    error = Color(0xFFB3261E),
    onError = Color.White,
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFFEFB8C8),
    onTertiary = Color(0xFF492532),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5)
)

@Composable
fun CookEaseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}