package rx.dagger.pseudo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import rx.dagger.pseudo.ui.theme.TelegramPseudoTheme

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { AppTopBar("Аккаунты") }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

        }
    }
}