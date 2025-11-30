package rx.dagger.pseudo.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import rx.dagger.pseudo.R
import rx.dagger.pseudo.data.countriesWithISOCodes

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2.0F

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

@Composable
fun CountrySelect(
    enabled: Boolean = true,
    countryName: String,
    onClick: () -> Unit
) {
    var textColor: Color = Color.White
    var borderColor = Color(0x22FFFFFF)

    if (!enabled) {
        textColor = Color(0x51FFFFFF)
        borderColor = Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bottomBorder(1.dp, borderColor)
            .padding(16.dp, 12.dp)
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = countryName,
            style = MaterialTheme.typography.labelSmall.copy(color = textColor)
        )
        Icon(
            painter = painterResource(R.drawable.ic_caret_down),
            contentDescription = "Caret Down"
        )
    }
}

@Composable
fun textFieldColorScheme(): TextFieldColors {
    val underlineColor = Color(0x22FFFFFF)
    val containerColor = Color(0x00000000)

    return TextFieldDefaults.colors(
        errorContainerColor = containerColor,
        focusedContainerColor = containerColor,
        disabledContainerColor = containerColor,
        unfocusedContainerColor = containerColor,
        focusedIndicatorColor = Color(0xFF3476AA),
        unfocusedIndicatorColor = underlineColor,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Red
    )
}

@Composable
fun PhoneInput(
    enabled: Boolean = true,
    error: String? = null,
    codeValue: String,
    onCodeValueChange: (String) -> Unit,
    phoneValue: String,
    onPhoneValueChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TextField(
                value = codeValue,
                prefix = { Text("+") },
                singleLine = true,
                isError = error != null,
                onValueChange = { newValue ->
                    val filtered = newValue
                        .trim()
                        .replace(" ", "")
                        .takeWhile { it.isDigit() }
                    onCodeValueChange(filtered)
                },
                colors = textFieldColorScheme(),
                enabled = enabled,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                modifier = Modifier.width(100.dp)
            )
            TextField(
                value = phoneValue,
                singleLine = true,
                isError = error != null,
                onValueChange = { newValue ->
                    val filtered = newValue
                        .trim()
                        .replace(" ", "")
                        .takeWhile { it.isDigit() }
                    onPhoneValueChange(filtered)
                },
                colors = textFieldColorScheme(),
                enabled = enabled,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row() {
            if (error != null) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun FormButton(
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp, 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF3476AA),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun CountrySelectOverlay(
    onSelected: (String, String) -> Unit
) {
    val countryCodeLst = remember { countriesWithISOCodes() }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(countryCodeLst.size) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onSelected(countryCodeLst[it].countryName,
                                    countryCodeLst[it].countryCode)
                            }
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp)
                    ) {
                        Text(
                            text = countryCodeLst[it].countryName,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CodeInput(
    value: String,
    error: String?,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = value,
            singleLine = true,
            isError = error != null,
            onValueChange = { newValue ->
                val filtered = newValue
                    .trim()
                    .replace(" ", "")
                    .takeWhile { it.isDigit() }
                onValueChange(filtered)
            },
            colors = textFieldColorScheme(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxWidth()
        )
        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun PasswordInput(
    value: String,
    error: String?,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = value,
            singleLine = true,
            isError = error != null,
            onValueChange = { newValue ->
                onValueChange(newValue)
            },
            colors = textFieldColorScheme(),
            enabled = enabled,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}