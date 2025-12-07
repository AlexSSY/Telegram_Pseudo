package rx.dagger.pseudo.presentation.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import rx.dagger.pseudo.Proto
import rx.dagger.pseudo.ProtoFormVisibilityState
import rx.dagger.pseudo.data.countriesWithISOCodes
import rx.dagger.pseudo.presentation.CountrySelect
import rx.dagger.pseudo.presentation.CountrySelectOverlay
import rx.dagger.pseudo.presentation.FormButton
import rx.dagger.pseudo.presentation.PhoneInput
import rx.dagger.pseudo.viewmodel.AddAccountViewModel

@Composable
fun PhoneForm(
    viewModel: AddAccountViewModel,
    onBackClick: (callback: () -> Boolean) -> Unit
) {
    var phoneCode by remember { mutableStateOf("380") }
    var phoneNumber by remember { mutableStateOf("") }
    var countryName by remember { mutableStateOf("Украина") }
    var countrySelectOpened by remember { mutableStateOf(false) }
    val loading = viewModel.loadingSafe.collectAsState()
    val error = viewModel.errorSafe.collectAsState()

    onBackClick({
        if (countrySelectOpened) {
            countrySelectOpened = false
            return@onBackClick false
        }

        return@onBackClick true
    })

    FormSkeleton(
        title = "Ваш номер телефона",
        subTitle = "Проверьте код страны и введите свой номер телефона.",
    ) {
        CountrySelect(
            enabled = !loading.value,
            countryName = countryName,
            onClick = { countrySelectOpened = true }
        )
        PhoneInput(
            enabled = !loading.value,
            error = error.value,
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
            enabled = !loading.value,
            text = "Продолжить",
            onClick = {
                viewModel.sendPhoneNumber(phoneCode, phoneNumber)
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

@Composable
fun FormSkeleton(
    title: String,
    subTitle: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(30.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = subTitle,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0x91FFFFFF)
            )
        }
        content()
    }
}