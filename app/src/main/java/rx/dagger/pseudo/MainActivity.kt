package rx.dagger.pseudo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rx.dagger.pseudo.ui.theme.TelegramPseudoTheme
import rx.dagger.pseudo.presentation.screens.AddAccountScreen
import rx.dagger.pseudo.presentation.screens.HomeScreen
import rx.dagger.pseudo.viewmodel.AddAccountViewModel
import rx.dagger.pseudo.viewmodel.AddAccountViewModelFactory
import rx.dagger.pseudo.viewmodel.HomeViewModel
import rx.dagger.pseudo.viewmodel.HomeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepOnScreen by mutableStateOf(true)
        splashScreen.setKeepOnScreenCondition { keepOnScreen }
        lifecycleScope.launch {
            delay(1500L)
            keepOnScreen = false
        }
        super.onCreate(savedInstanceState)

        val app = application as App
        val repo = app.telegramClientRepository

        val addAccountViewModel: AddAccountViewModel by viewModels {
            AddAccountViewModelFactory(repo)
        }

        val homeViewModel: HomeViewModel by viewModels {
            HomeViewModelFactory(repo)
        }

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            TelegramPseudoTheme(
                darkTheme = true
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screens.Home
                ) {
                    composable<Screens.Home> {
                        HomeScreen(homeViewModel)
                    }
                    composable<Screens.AddAccount> {
                        AddAccountScreen(addAccountViewModel)
                    }
                }
            }
        }
    }
}