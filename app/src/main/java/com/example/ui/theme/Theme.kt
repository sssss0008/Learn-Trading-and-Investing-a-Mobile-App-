package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

enum class AppThemeMode {
    DARK,
    LIGHT,
    AMOLED,
    HIGH_CONTRAST
}

data class CustomFinancialColors(
    val bullish: Color,
    val bullishBg: Color,
    val bearish: Color,
    val bearishBg: Color,
    val warning: Color,
    val warningBg: Color,
    val cardBackground: Color,
    val cardBorder: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val isDark: Boolean
)

val LocalFinancialColors = staticCompositionLocalOf {
    CustomFinancialColors(
        bullish = EmeraldBullish,
        bullishBg = EmeraldBullishBg,
        bearish = RoseBearish,
        bearishBg = RoseBearishBg,
        warning = AmberGold,
        warningBg = AmberGoldBg,
        cardBackground = SurfaceCardDark,
        cardBorder = BorderDark,
        textSecondary = TextSecondaryDark,
        textMuted = TextMutedDark,
        isDark = true
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = CyanAccent,
    onPrimary = BackgroundDark,
    primaryContainer = CyanAccentDark,
    onPrimaryContainer = TextPrimaryDark,
    secondary = EmeraldBullish,
    onSecondary = BackgroundDark,
    tertiary = AmberGold,
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceElevatedDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = BorderDark
)

private val LightColorScheme = lightColorScheme(
    primary = CyanAccentDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCCFBF7),
    onPrimaryContainer = Color(0xFF003834),
    secondary = EmeraldBullish,
    onSecondary = Color.White,
    tertiary = AmberGold,
    background = BackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceElevatedLight,
    onSurfaceVariant = TextSecondaryLight,
    outline = BorderLight
)

private val AmoledColorScheme = darkColorScheme(
    primary = CyanAccent,
    onPrimary = Color.Black,
    primaryContainer = SurfaceCardAmoled,
    onPrimaryContainer = Color.White,
    secondary = EmeraldBullish,
    onSecondary = Color.Black,
    tertiary = AmberGold,
    background = BackgroundAmoled,
    onBackground = Color.White,
    surface = SurfaceAmoled,
    onSurface = Color.White,
    surfaceVariant = SurfaceElevatedAmoled,
    onSurfaceVariant = Color(0xFFB0B0B0),
    outline = BorderAmoled
)

private val HighContrastColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF222222),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF00FFCC),
    onSecondary = Color.Black,
    tertiary = Color(0xFFFFDD00),
    background = BackgroundHighContrast,
    onBackground = TextPrimaryHighContrast,
    surface = SurfaceHighContrast,
    onSurface = TextPrimaryHighContrast,
    surfaceVariant = SurfaceHighContrast,
    onSurfaceVariant = TextSecondaryHighContrast,
    outline = BorderHighContrast
)

@Composable
fun TradeInvestTheme(
    themeMode: AppThemeMode = AppThemeMode.DARK,
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        AppThemeMode.DARK -> DarkColorScheme
        AppThemeMode.LIGHT -> LightColorScheme
        AppThemeMode.AMOLED -> AmoledColorScheme
        AppThemeMode.HIGH_CONTRAST -> HighContrastColorScheme
    }

    val customColors = when (themeMode) {
        AppThemeMode.DARK -> CustomFinancialColors(
            bullish = EmeraldBullish,
            bullishBg = EmeraldBullishBg,
            bearish = RoseBearish,
            bearishBg = RoseBearishBg,
            warning = AmberGold,
            warningBg = AmberGoldBg,
            cardBackground = SurfaceCardDark,
            cardBorder = BorderDark,
            textSecondary = TextSecondaryDark,
            textMuted = TextMutedDark,
            isDark = true
        )
        AppThemeMode.LIGHT -> CustomFinancialColors(
            bullish = Color(0xFF059669),
            bullishBg = Color(0x1A059669),
            bearish = Color(0xFFE11D48),
            bearishBg = Color(0x1AE11D48),
            warning = Color(0xFFD97706),
            warningBg = Color(0x1AD97706),
            cardBackground = SurfaceCardLight,
            cardBorder = BorderLight,
            textSecondary = TextSecondaryLight,
            textMuted = TextMutedLight,
            isDark = false
        )
        AppThemeMode.AMOLED -> CustomFinancialColors(
            bullish = EmeraldBullish,
            bullishBg = Color(0x3310B981),
            bearish = RoseBearish,
            bearishBg = Color(0x33F43F5E),
            warning = AmberGold,
            warningBg = Color(0x33F59E0B),
            cardBackground = SurfaceCardAmoled,
            cardBorder = BorderAmoled,
            textSecondary = Color(0xFFA0A0A0),
            textMuted = Color(0xFF6E6E6E),
            isDark = true
        )
        AppThemeMode.HIGH_CONTRAST -> CustomFinancialColors(
            bullish = Color(0xFF00FF66),
            bullishBg = Color(0x3300FF66),
            bearish = Color(0xFFFF3366),
            bearishBg = Color(0x33FF3366),
            warning = Color(0xFFFFEE00),
            warningBg = Color(0x33FFEE00),
            cardBackground = SurfaceCardHighContrast,
            cardBorder = BorderHighContrast,
            textSecondary = TextSecondaryHighContrast,
            textMuted = Color(0xFFB0B0B0),
            isDark = true
        )
    }

    CompositionLocalProvider(LocalFinancialColors provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
