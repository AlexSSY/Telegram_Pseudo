package rx.dagger.pseudo.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rx.dagger.pseudo.data.TelegramClient
import rx.dagger.pseudo.data.TelegramClientRepository

class HomeViewModel(
    private val telegramClientRepository: TelegramClientRepository
) : ViewModel() {
    private val loading = MutableStateFlow(false)
    val loadingSafe = loading.asStateFlow()
    val accountsSafe = telegramClientRepository.telegramClients
}