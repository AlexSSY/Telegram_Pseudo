package rx.dagger.pseudo.presentation.forms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import rx.dagger.pseudo.Proto
import rx.dagger.pseudo.presentation.CodeInput
import rx.dagger.pseudo.presentation.FormButton
import rx.dagger.pseudo.presentation.PasswordInput
import rx.dagger.pseudo.viewmodel.AddAccountViewModel

@Composable
fun PasswordForm(
    viewModel: AddAccountViewModel,
    onBackClick: (callback: () -> Unit) -> Unit
) {
    var password by remember { mutableStateOf("") }
    var loading = viewModel.loadingSafe.collectAsState()
    var error = viewModel.errorSafe.collectAsState()

    FormSkeleton(
        title = "2FA Пароль",
        subTitle = "Введите пароль от вашего аккаунта Telegram.",
    ) {
        PasswordInput(
            value = password,
            error = error.value,
            onValueChange = { password = it },
            enabled = !loading.value
        )
        FormButton(
            enabled = !loading.value,
            text =  "Продолжить",
            onClick = {
                viewModel.sendPassword(password)
            }
        )
    }
}