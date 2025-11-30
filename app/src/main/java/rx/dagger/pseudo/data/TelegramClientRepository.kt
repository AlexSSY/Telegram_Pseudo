package rx.dagger.pseudo.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelegramClientRepository {
    private val telegramClientsFlow = MutableStateFlow<List<TelegramClient>>(emptyList())
    val telegramClients = telegramClientsFlow.asStateFlow()

    init {
        // here is load list of databaseDirs from SQLite DB
        // and initialize against each of them instance of TelegramClient
        // and add instance to teleggramClientsFlow
    }

    fun save(telegramClient: TelegramClient) {
        val dbDataToSave = telegramClient.databaseDirectorySafe
    }
}