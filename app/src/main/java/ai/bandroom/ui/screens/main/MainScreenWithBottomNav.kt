package ai.bandroom.ui.screens.main

import ai.bandroom.ui.navigation.NavScreen
import ai.bandroom.ui.screens.main.tabs.HomeTab
import ai.bandroom.ui.screens.main.tabs.LessonsTab
import ai.bandroom.ui.screens.main.tabs.ProfileTab
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.foundation.layout.padding

@Composable
fun MainScreenWithBottomNav(navController: NavHostController) {
    val bottomNavController = rememberNavController()
    val items = NavScreen.bottomNavItems

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute = bottomNavController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            bottomNavController.navigate(screen.route) {
                                popUpTo(bottomNavController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        alwaysShowLabel = false, // ✅ Hides the label
//                        label = { Text(screen.label) } // You can keep this or remove it; doesn't show if `alwaysShowLabel = false`
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = NavScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavScreen.Home.route) { HomeTab() }
            composable(NavScreen.Lessons.route) { LessonsTab() }
            composable(NavScreen.Profile.route) {
                ProfileTab(navController) // ✅ uses root navController for logout
            }
        }
    }
}
