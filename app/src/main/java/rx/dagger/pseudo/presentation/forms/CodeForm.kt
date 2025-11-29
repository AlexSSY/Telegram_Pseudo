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

@Composable
fun CodeForm(
    protoViewModel: Proto,
    onBackClick: (callback: () -> Unit) -> Unit
) {
    var code by remember { mutableStateOf("") }
    var loading = protoViewModel.loading.collectAsState()
    var error = protoViewModel.error.collectAsState()

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
                protoViewModel.sendCode(code)
            }
        )
    }
}