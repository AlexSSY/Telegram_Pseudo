package rx.dagger.pseudo

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onBackAction: (() -> Unit)? = null
) {
    if (onBackAction == null) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title
                )
            }
        )
    } else {
        CenterAlignedTopAppBar(
            title = { Text(title) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground
            ),
            navigationIcon = {
                IconButton(
                    onClick = onBackAction
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_button_back),
                        contentDescription = "Navigation Back"
                    )
                }
            }
        )
    }
}