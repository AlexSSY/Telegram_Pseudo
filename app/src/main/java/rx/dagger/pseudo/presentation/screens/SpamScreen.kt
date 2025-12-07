package rx.dagger.pseudo.presentation.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import rx.dagger.pseudo.AppTopBar
import rx.dagger.pseudo.R

@Composable
fun SpamScreen(
    onBackNavigation: () -> Unit
) {
    var message by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Спам",
                onBackAction = {
                    onBackNavigation.invoke()
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(Color.Transparent)
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(
                            R.drawable.ic_attach_button,
                        ),
                        contentDescription = "Attach"
                    )
                }
                TextField(
                    modifier = Modifier
                        .weight(1.0f),
                    onValueChange = { message = it },
                    value = message
                )
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(
                            R.drawable.ic_telegram,
                        ),
                        contentDescription = "Send"
                    )
                }
            }
        }
    }
}