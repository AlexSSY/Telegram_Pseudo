package rx.dagger.pseudo

import android.app.Application
import rx.dagger.pseudo.data.AppDatabase
import rx.dagger.pseudo.data.TelegramClientRepository

class App: Application() {
    val database by lazy { AppDatabase.createDatabase(this) }
    lateinit var telegramClientRepository: TelegramClientRepository
        private set

    override fun onCreate() {
        super.onCreate()
        telegramClientRepository = TelegramClientRepository(this)
    }
}