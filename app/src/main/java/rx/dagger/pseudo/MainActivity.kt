package rx.dagger.pseudo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rx.dagger.pseudo.ui.theme.TelegramPseudoTheme
import org.drinkless.tdlib.Client;
import rx.dagger.pseudo.presentation.screens.AddAccountScreen
import rx.dagger.pseudo.viewmodel.AddAccountViewModel
import rx.dagger.pseudo.viewmodel.AddAccountViewModelFactory

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

        val viewModel: AddAccountViewModel by viewModels {
            AddAccountViewModelFactory(repo)
        }

        enableEdgeToEdge()
        setContent {
            TelegramPseudoTheme(
                darkTheme = true
            ) {
                AddAccountScreen(viewModel)
            }
        }
    }
}