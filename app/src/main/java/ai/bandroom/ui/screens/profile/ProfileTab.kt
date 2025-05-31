package ai.bandroom.ui.screens.main.tabs

import ai.bandroom.viewmodel.ProfileViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileTab(
    navController: NavController,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val logoutState by viewModel.logoutSuccess.collectAsState()

    // 🔄 Observe logout state to navigate
    LaunchedEffect(logoutState) {
        println("🚪 Logout state observed: $logoutState")
        if (logoutState == true) {
            println("✅ Navigating to login screen after logout")
            navController.navigate("login") {
                popUpTo("main") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("👤 Profile Screen", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            println("👆 Logout button clicked")
            viewModel.logout()
        }) {
            Text("Logout")
        }
    }
}
