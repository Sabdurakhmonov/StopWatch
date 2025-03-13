package uz.abdurakhmonov.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import uz.abdurakhmonov.stopwatch.screen.home_screen.HomeScreenContent
import uz.abdurakhmonov.stopwatch.screen.home_screen.HomeScreenVM
import uz.abdurakhmonov.stopwatch.screen.home_screen.HomeScreenVMImpl
import uz.abdurakhmonov.stopwatch.ui.theme.StopWatchTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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