package abandonedspace.android.y2y.domain.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import abandonedspace.android.y2y.presentation.achievements_list.AchievementsScreen

enum class SCREENS(val route: Route, val content: Content) {
    ACHIEVEMENTS(Route("ACHIEVEMENTS"), { AchievementsScreen() }),
}

@JvmInline
value class Route(val value: String) {

    init {
        require(value.isNotBlank())
    }
}

private typealias Content = @Composable (NavBackStackEntry) -> Unit