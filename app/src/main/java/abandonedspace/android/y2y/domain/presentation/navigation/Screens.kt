package abandonedspace.android.y2y.domain.presentation.navigation

import abandonedspace.android.y2y.presentation.add_category.AddCategoryScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

enum class SCREENS(val route: Route, val content: Content) {
    ADD_CATEGORY(Route("ADD_CATEGORY"), { AddCategoryScreen() }),
    ADD_ACHIEVEMENT(Route("ADD_ACHIEVEMENT"), { /*TODO*/ }),
    ACHIEVEMENTS(Route("ACHIEVEMENTS"), { /*TODO*/ }),
}

@JvmInline
value class Route(val value: String) {

    init {
        require(value.isNotBlank())
    }
}

private typealias Content = @Composable (NavBackStackEntry) -> Unit