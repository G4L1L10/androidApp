package ai.bandroom.ui.screens.splash

import ai.bandroom.viewmodel.SplashViewModel
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = koinViewModel()
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState(initial = null)

    LaunchedEffect(isLoggedIn) {
        delay(1500) // Optional splash delay
        when (isLoggedIn) {
            true -> navController.navigate("main") {
                popUpTo("splash") { inclusive = true }
            }
            false -> navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
            else -> {} // Do nothing yet
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
