package ai.bandroom.ui.screens.signup

import ai.bandroom.viewmodel.SignupViewModel
import ai.bandroom.viewmodel.state.AuthUiEvent
import ai.bandroom.ui.components.PrimaryButton
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController

@Composable
fun SignupScreen(viewModel: SignupViewModel, navController: NavController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isSuccess) {
        LaunchedEffect(Unit) {
            navController.navigate("login") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sign Up", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEvent(AuthUiEvent.EmailChanged(it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onEvent(AuthUiEvent.PasswordChanged(it)) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.error != null) {
            Text(text = uiState.error!!, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        PrimaryButton(
            text = if (uiState.isLoading) "Signing up..." else "Sign Up",
            onClick = { viewModel.onEvent(AuthUiEvent.Submit) },
            enabled = !uiState.isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text("Already have an account? Log in")
        }
    }
}
