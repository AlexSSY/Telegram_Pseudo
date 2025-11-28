package rx.dagger.pseudo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi

class TelegramViewModel : ViewModel() {
    val _log = MutableStateFlow<List<String>>(emptyList())
    val log = _log.asStateFlow()

    init {
        Client.create(
            { data -> _log.update { it + data.toString() } } ,
            { data -> _log.update { it + (data.message ?: "error") } },
            { data -> _log.update { it + (data.message ?: "error") } }
        )
    }
}