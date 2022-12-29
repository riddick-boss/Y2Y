package abandonedspace.android.y2y.domain.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Orange500,
    primaryVariant = Purple200,
    secondary = Blue200,
    background = PurpleDark,
    surface = YellowDark
)

private val LightColorPalette = lightColors(
    primary = Orange500,
    primaryVariant = Purple200,
    secondary = Blue200,
    background = Purple200,
    surface = YellowLight
)

@Composable
fun Y2YTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}