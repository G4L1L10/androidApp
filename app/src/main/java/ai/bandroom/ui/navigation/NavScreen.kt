package ai.bandroom.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavScreen(val route: String, val label: String, val icon: ImageVector) {
    object Home : NavScreen("home", "Home", Icons.Default.Home)
    object Lessons : NavScreen("lessons", "Lessons", Icons.Default.School)
    object Profile : NavScreen("profile", "Profile", Icons.Default.Person)

    companion object {
        val bottomNavItems = listOf(Home, Lessons, Profile)
    }
}
