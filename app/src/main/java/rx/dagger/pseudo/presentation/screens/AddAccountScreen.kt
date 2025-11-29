package rx.dagger.pseudo.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rx.dagger.pseudo.AppTopBar
import rx.dagger.pseudo.presentation.forms.PhoneForm

@Composable
fun AddAccountScreen() {
    var onBackClick: (() -> Unit)? = remember { null }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Добавить аккаунт",
                onBackAction = {
                    onBackClick?.invoke()
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            PhoneForm({
                onBackClick = it
            })
        }
    }
}