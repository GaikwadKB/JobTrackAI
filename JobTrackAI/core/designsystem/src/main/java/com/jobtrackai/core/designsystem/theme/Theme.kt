package com.jobtrackai.core.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Which of Section 45's three theme options is active. Deliberately not
 * just a nullable `Boolean?` — a named enum reads correctly at every call
 * site (`ThemeMode.System` vs. `null`) and is what `feature:settings`
 * (Section 44) will persist to `core:datastore` once that screen exists.
 *
 * [JobTrackTheme] takes this as a plain parameter rather than reading a
 * preference itself, so this module stays free of a `core:datastore`
 * dependency — the composition root (`MainActivity` / nav host) is
 * responsible for collecting the persisted preference and passing it down,
 * once Phase 44/settings wires that up. Until then, callers simply omit
 * the parameter and get [System].
 */
enum class ThemeMode {
    System,
    Light,
    Dark,
}

/**
 * Root theme composable — every screen in the app is wrapped in this
 * (applied once, in the nav host, not per-screen).
 *
 * @param themeMode resolves light vs. dark; defaults to following the OS.
 * @param dynamicColor when true and running on Android 12+, derives the
 *   color scheme from the user's wallpaper (Material You) instead of the
 *   fixed [JobTrackColors] brand palette. Defaults to false: a portfolio
 *   app benefits more from a consistent, intentional brand look in
 *   screenshots/demo video (Section 53) than from per-device theming.
 */
@Composable
fun JobTrackTheme(
    themeMode: ThemeMode = ThemeMode.System,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val useDarkTheme = when (themeMode) {
        ThemeMode.System -> isSystemInDarkTheme()
        ThemeMode.Light -> false
        ThemeMode.Dark -> true
    }

    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)

        useDarkTheme -> darkColorScheme(
            primary = JobTrackColors.Primary80,
            onPrimary = JobTrackColors.Primary20,
            primaryContainer = JobTrackColors.Primary30,
            onPrimaryContainer = JobTrackColors.Primary90,
            secondary = JobTrackColors.Secondary80,
            onSecondary = JobTrackColors.Secondary20,
            secondaryContainer = JobTrackColors.Secondary30,
            onSecondaryContainer = JobTrackColors.Secondary90,
            tertiary = JobTrackColors.Tertiary80,
            onTertiary = JobTrackColors.Tertiary20,
            tertiaryContainer = JobTrackColors.Tertiary30,
            onTertiaryContainer = JobTrackColors.Tertiary90,
            error = JobTrackColors.Error80,
            onError = JobTrackColors.Error20,
            errorContainer = JobTrackColors.Error30,
            onErrorContainer = JobTrackColors.Error90,
            background = JobTrackColors.Neutral10,
            onBackground = JobTrackColors.Neutral90,
            surface = JobTrackColors.Neutral10,
            onSurface = JobTrackColors.Neutral90,
            surfaceVariant = JobTrackColors.NeutralVariant30,
            onSurfaceVariant = JobTrackColors.NeutralVariant80,
            outline = JobTrackColors.NeutralVariant50,
        )

        else -> lightColorScheme(
            primary = JobTrackColors.Primary40,
            onPrimary = JobTrackColors.Neutral99,
            primaryContainer = JobTrackColors.Primary90,
            onPrimaryContainer = JobTrackColors.Primary10,
            secondary = JobTrackColors.Secondary40,
            onSecondary = JobTrackColors.Neutral99,
            secondaryContainer = JobTrackColors.Secondary90,
            onSecondaryContainer = JobTrackColors.Secondary10,
            tertiary = JobTrackColors.Tertiary40,
            onTertiary = JobTrackColors.Neutral99,
            tertiaryContainer = JobTrackColors.Tertiary90,
            onTertiaryContainer = JobTrackColors.Tertiary10,
            error = JobTrackColors.Error40,
            onError = JobTrackColors.Neutral99,
            errorContainer = JobTrackColors.Error90,
            onErrorContainer = JobTrackColors.Error10,
            background = JobTrackColors.Neutral99,
            onBackground = JobTrackColors.Neutral10,
            surface = JobTrackColors.Neutral99,
            onSurface = JobTrackColors.Neutral10,
            surfaceVariant = JobTrackColors.NeutralVariant90,
            onSurfaceVariant = JobTrackColors.NeutralVariant30,
            outline = JobTrackColors.NeutralVariant50,
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = JobTrackTypography,
        shapes = JobTrackShapes,
        content = content,
    )
}
