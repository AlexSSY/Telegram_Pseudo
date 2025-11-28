package rx.dagger.pseudo.data

class TelegramClient(
    private val databaseDirectory: String?
) {
    private val resolvedDatabaseDirectory: String =
        databaseDirectory ?: System.currentTimeMillis().toString()

    val databaseDirectorySafe: String
        get() = resolvedDatabaseDirectory
}