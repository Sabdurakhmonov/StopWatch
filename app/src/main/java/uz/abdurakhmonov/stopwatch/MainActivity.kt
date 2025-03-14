package uz.abdurakhmonov.stopwatch

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import uz.abdurakhmonov.stopwatch.screen.home_screen.HomeScreenContent
import uz.abdurakhmonov.stopwatch.screen.home_screen.HomeScreenVM
import uz.abdurakhmonov.stopwatch.screen.home_screen.HomeScreenVMImpl
import uz.abdurakhmonov.stopwatch.ui.theme.StopWatchTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StopWatchTheme {
                val viewmodel: HomeScreenVM = hiltViewModel<HomeScreenVMImpl>()
                HomeScreenContent(viewmodel)
            }
        }
    }
}