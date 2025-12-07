package rx.dagger.pseudo.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rx.dagger.pseudo.AppTopBar

@Composable
fun SpamScreen(
    onBackNavigation: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Спам",
                onBackAction = {
                    onBackNavigation.invoke()
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {

        }
    }
}