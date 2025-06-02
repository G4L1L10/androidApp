package ai.bandroom.ui.navigation

import ai.bandroom.ui.screens.login.LoginScreen
import ai.bandroom.ui.screens.signup.SignupScreen
import ai.bandroom.ui.screens.main.MainScreenWithBottomNav
import ai.bandroom.ui.screens.splash.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("signup") {
            SignupScreen(navController = navController)
        }
        composable("main") {
            MainScreenWithBottomNav(navController = navController)
        }
    }
}
