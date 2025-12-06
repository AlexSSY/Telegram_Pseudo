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
import rx.dagger.pseudo.viewmodel.AddAccountViewModel

@Composable
fun CodeForm(
    viewModel: AddAccountViewModel,
    onBackClick: (callback: () -> Unit) -> Unit
) {
    var code by remember { mutableStateOf("") }
    val loading = viewModel.loadingSafe.collectAsState()
    val error = viewModel.errorSafe.collectAsState()

    FormSkeleton(
        title = "+1 123 456789",
        subTitle = "Если Вы используете приложение на других устройтвах, код для вохода был отправлен через Telegram.",
    ) {
        CodeInput(
            value = code,
            error = error.value,
            onValueChange = { code = it },
            enabled = !loading.value
        )
        FormButton(
            enabled = !loading.value,
            text =  "Продолжить",
            onClick = {
                viewModel.sendCode(code)
            }
        )
    }
}