package ai.bandroom

import ai.bandroom.ui.navigation.NavGraph
import ai.bandroom.ui.theme.BandroomTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BandroomRoot()
        }
    }
}

@Composable
fun BandroomRoot() {
    BandroomTheme {
        Surface {
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }
    }
}
