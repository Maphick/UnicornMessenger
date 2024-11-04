package com.mariiadeveloper.unicornmessenger.presentation.ui.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorScheme = darkColorScheme(
    primary = primary_dark,
    secondary = secondary_dark,
    tertiary = tertiary_dark,
    background = background_dark,
    surface = surface_dark,
    surfaceVariant = surface_variant_dark,
    secondaryContainer = secondary_container_dark,
    onPrimary = Color.Black,
    onSecondary = on_secondary_dark,
    onSecondaryContainer = on_secondary_container_dark,
    onTertiary = Color.White,
    onBackground = on_background_dark,
    onSurface = on_surface_dark,
    onSurfaceVariant = on_surface_variant_dark,
    onError = on_error_dark,
    primaryContainer = primary_container_dark,
    inversePrimary = inverse_primary_dark,
    inverseSurface = inverse_surface_dark,
    errorContainer = error_container_dark
)

private val LightColorScheme = lightColorScheme(
    primary = primary_light ,
    secondary = secondary_light,
    tertiary = tertiary_light,
    background =  background_light,
    surface = surface_light,
    surfaceTint = surface_container_low_light,
    surfaceVariant = surface_variant_light,
    secondaryContainer = secondary_container_light,
    onPrimary = Color.White,
    onSecondary = on_secondary_light,
    onSecondaryContainer = on_secondary_container_light,
    onTertiary = Color.Black,
    onBackground = on_background_light,
    onSurface = on_surface_light,
    onSurfaceVariant = on_surface_variant_light,
    onError = on_error_light,
    primaryContainer = primary_container_light,
    inversePrimary = inverse_primary_light,
    inverseSurface = inverse_surface_light,
    errorContainer = error_container_light

)
@SuppressLint("SuspiciousIndentation")
@Composable
fun UnicornMessengerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    //dynamicColor: Boolean = true,
    content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        //typography = kotlin.text.Typography,
        content = content
    )
}