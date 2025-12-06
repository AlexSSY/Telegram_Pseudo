package rx.dagger.pseudo.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rx.dagger.pseudo.App
import java.io.File

class TelegramClientRepository(
    private val context: Context
) {
    private val telegramClientsFlow = MutableStateFlow<List<TelegramClient>>(emptyList())
    val telegramClients = telegramClientsFlow.asStateFlow()

    private val db = (context as App).database
    private val accountDao = db.telegramAccountDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            accountDao.getAllAccountsFlow().collect { accounts ->
                val clients = accounts.map { entity ->
                    TelegramClient(databaseDirectory = entity.databaseDirectory)
                }
                telegramClientsFlow.value = clients
            }
        }
    }

    fun createTelegramClient(): TelegramClient {
        val name = System.currentTimeMillis().toString()
        val dir = File(context.filesDir, "td_$name")
        dir.mkdirs()

        return TelegramClient(dir.absolutePath)
    }

    suspend fun save(telegramClient: TelegramClient) {
        val dbDataToSave = telegramClient.databaseDirectorySafe
        accountDao.insert(
            TelegramAccountEntity(
                databaseDirectory = dbDataToSave
            )
        )
    }
}