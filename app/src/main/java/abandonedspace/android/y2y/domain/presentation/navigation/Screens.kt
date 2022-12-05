package abandonedspace.android.y2y.domain.presentation.navigation

import abandonedspace.android.y2y.presentation.add_category.AddCategoryScreen
import abandonedspace.android.y2y.presentation.add_achievement.AddAchievementScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

enum class SCREENS(val route: Route, val content: Content) {
    ADD_CATEGORY(Route("ADD_CATEGORY"), { _, _ -> AddCategoryScreen() }),
    ADD_ACHIEVEMENT(Route("ADD_ACHIEVEMENT"), { _, navController -> AddAchievementScreen(navController = navController) }),
    ACHIEVEMENTS(Route("ACHIEVEMENTS"), { _, navController -> /*TODO*/ }),
}

@JvmInline
value class Route(val value: String) {

    init {
        require(value.isNotBlank())
    }
}

private typealias Content = @Composable (NavBackStackEntry, NavController) -> Unit