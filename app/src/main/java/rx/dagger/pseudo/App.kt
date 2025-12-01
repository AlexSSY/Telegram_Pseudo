package rx.dagger.pseudo

import android.app.Application
import rx.dagger.pseudo.data.AppDatabase

class App: Application() {
    val database by lazy { AppDatabase.createDatabase(this) }
}