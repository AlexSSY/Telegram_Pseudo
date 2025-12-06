package rx.dagger.pseudo.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rx.dagger.pseudo.AppTopBar
import rx.dagger.pseudo.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val clients = viewModel.accountsSafe.collectAsState()

    Scaffold(
        topBar = { AppTopBar("Аккаунты") },
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FloatingActionButton(onClick = { /* Handle FAB click */ }) {
                    Icon(Icons.Filled.Email, "Add new item")
                }
                FloatingActionButton(onClick = { /* Handle FAB click */ }) {
                    Icon(Icons.Filled.Add, "Add new item")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(clients.value.size) { idx ->
                val client = clients.value[idx]
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf<String>(
                        client.authorizationState.value.toString().takeWhile { it != ' ' },
                        client.phoneNumber.value ?: "loading ...",
                        client.fullName.value ?: "loading ..."
                    ).forEach { str ->
                        Text(
                            text = str,
                            fontSize = 8.sp
                        )
                    }
                }
            }
        }
    }
}