package abandonedspace.android.y2y.domain.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.ADD_ACHIEVEMENT) {
        composable(Screens.ADD_CATEGORY) { /*TODO*/ }
        composable(Screens.ADD_ACHIEVEMENT) { /*TODO*/ }
        composable(Screens.ACHIEVEMENTS) { /*TODO*/ }
    }
}