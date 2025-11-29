package rx.dagger.pseudo.presentation.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import rx.dagger.pseudo.ProtoFormVisibilityState
import rx.dagger.pseudo.data.countriesWithISOCodes
import rx.dagger.pseudo.presentation.CountrySelect
import rx.dagger.pseudo.presentation.CountrySelectOverlay
import rx.dagger.pseudo.presentation.FormButton
import rx.dagger.pseudo.presentation.PhoneInput
import rx.dagger.pseudo.protoSendAnything

@Composable
fun PhoneForm(
    onBackClick: (callback: () -> Unit) -> Unit
) {
    var phoneCode by remember { mutableStateOf("380") }
    var phoneNumber by remember { mutableStateOf("") }
    var countryName by remember { mutableStateOf("Украина") }
    var countrySelectOpened by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var error: String? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

    onBackClick({
        countrySelectOpened = false
    })

    Column(
        modifier = Modifier.padding(60.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Ваш номер телефона",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "Проверьте код страны и введите свой номер телефона.",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0x91FFFFFF)
            )
        }
        CountrySelect(!loading, countryName, { countrySelectOpened = true })
        PhoneInput(
            enabled = !loading,
            codeValue = phoneCode,
            onCodeValueChange = {
                phoneCode = it.trim()

                val iso = countriesWithISOCodes().find { it.countryCode == phoneCode }

                countryName = iso?.countryName ?: "Некорректный код страны"
            },
            phoneValue = phoneNumber,
            onPhoneValueChange = {
                phoneNumber = it
            }
        )
        FormButton(
            enabled = !loading,
            text = "Продолжить",
            onClick = {
                error = null

                TODO("impl pre-validation")

                scope.launch {
                    loading = true
                    protoSendAnything(ProtoFormVisibilityState.CODE)
                    loading = false
                }
            }
        )
    }
    if (countrySelectOpened) {
        CountrySelectOverlay(
            { selectedCountryName, selectedCountryCode ->
                countryName = selectedCountryName
                phoneCode = selectedCountryCode
                countrySelectOpened = false
            }
        )
    }
}