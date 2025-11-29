package rx.dagger.pseudo.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import rx.dagger.pseudo.AppTopBar
import rx.dagger.pseudo.Proto
import rx.dagger.pseudo.ProtoFormVisibilityState
import rx.dagger.pseudo.currentVisibility
import rx.dagger.pseudo.presentation.forms.PhoneForm

@Composable
fun AddAccountScreen() {
    var onBackClick: (() -> Unit)? = remember { null }
    val protoViewModel = remember { Proto() }
    val currentVisibility = protoViewModel.currentVisibility.collectAsState()

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
            when (currentVisibility.value) {
                ProtoFormVisibilityState.PHONE -> {
                    PhoneForm({
                        onBackClick = it
                    }, {
                            loading = true
                            protoSendAnything(ProtoFormVisibilityState.CODE)
                            loading = false
                    })
                }
                ProtoFormVisibilityState.CODE -> TODO()
                ProtoFormVisibilityState.PASSWORD -> TODO()
            }
        }
    }
}