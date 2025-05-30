package ai.bandroom.ui.navigation

import ai.bandroom.ui.screens.home.HomeScreen
import ai.bandroom.ui.screens.login.LoginScreen
import ai.bandroom.ui.screens.signup.SignupScreen
import ai.bandroom.viewmodel.LoginViewModel
import ai.bandroom.viewmodel.SignupViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val vm: LoginViewModel = koinViewModel()
            LoginScreen(viewModel = vm, navController = navController)
        }
        composable("signup") {
            val vm: SignupViewModel = koinViewModel()
            SignupScreen(viewModel = vm, navController = navController)
        }
        composable("home") {
            HomeScreen()
        }
    }
}
