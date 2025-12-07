package rx.dagger.pseudo.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.drinkless.tdlib.TdApi
import rx.dagger.pseudo.AppTopBar
import rx.dagger.pseudo.Proto
import rx.dagger.pseudo.ProtoFormVisibilityState
import rx.dagger.pseudo.presentation.forms.CodeForm
import rx.dagger.pseudo.presentation.forms.PasswordForm
import rx.dagger.pseudo.presentation.forms.PhoneForm
import rx.dagger.pseudo.viewmodel.AddAccountViewModel

@Composable
fun AddAccountScreen(
    viewModel: AddAccountViewModel,
    onBackNavigation: () -> Unit
) {
    var onBackClick: (() -> Boolean)? = remember { null }
    val authorizationState = viewModel.telegramClient.authorizationState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Добавить аккаунт",
                onBackAction = {
                    if (onBackClick?.invoke() == true)
                        onBackNavigation.invoke()
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
            when (authorizationState.value?.constructor) {
                TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR -> {
                    PhoneForm(
                        viewModel,
                        { onBackClick = it }
                    )
                }
                TdApi.AuthorizationStateWaitCode.CONSTRUCTOR -> {
                    CodeForm(
                        viewModel
                    ) { }
                }
                TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR -> {
                    PasswordForm(
                        viewModel
                    ) { }
                }
                else -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}