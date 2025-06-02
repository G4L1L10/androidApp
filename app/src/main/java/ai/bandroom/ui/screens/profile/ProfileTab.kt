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
    val email by viewModel.email.collectAsState()

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
        Spacer(modifier = Modifier.height(16.dp))

        if (email != null) {
            Text("Welcome, ${email?.substringBefore("@")}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("($email)", style = MaterialTheme.typography.bodySmall)
        } else {
            Text("Loading email...", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            println("👆 Logout button clicked")
            viewModel.logout()
        }) {
            Text("Logout")
        }
    }
}
