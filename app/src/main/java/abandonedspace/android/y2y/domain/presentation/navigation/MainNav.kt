package abandonedspace.android.y2y.domain.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SCREENS.ADD_ACHIEVEMENT.route.value) { // TODO: start destination
        SCREENS.values().forEach {
            composable(
                route = it.route.value,
                content = { navBackStackEntry ->
                    it.content(navBackStackEntry, navController)
                }
            )
        }
    }
}