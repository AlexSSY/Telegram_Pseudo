package rx.dagger.pseudo.data

import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi

class TelegramClient(
    private val databaseDirectory: String?
) {
    companion object {
        fun error(message: String) {
            System.err.println("TelegramClient -> $message")
        }
    }

    private val resolvedDatabaseDirectory: String =
        databaseDirectory ?: System.currentTimeMillis().toString()

    val databaseDirectorySafe: String
        get() = resolvedDatabaseDirectory

    private val client = Client.create(
        { updateHandler(it) },
        { updateExceptionHandler(it) },
        { defaultExceptionHandler(it) }
    )

    private var phoneNumber: String? = null
    private var fullName: String? = null

    private fun updateHandler(update: TdApi.Object) {
        when (update.constructor) {
            TdApi.UpdateAuthorizationState.CONSTRUCTOR -> {
                onAuthorizationStateUpdated(
                    (update as TdApi.UpdateAuthorizationState).authorizationState
                )
            }
        }
    }

    private fun onAuthorizationStateUpdated(authorizationState: TdApi.AuthorizationState?) {

    }

    private fun updateExceptionHandler(exception: Throwable) {
        val message = exception.message ?: "unknown"
        error("Update: $message")
    }

    private fun defaultExceptionHandler(exception: Throwable) {
        val message = exception.message ?: "unknown"
        error("Default: $message")
    }

    suspend fun getPhoneNumber(): String {
        if (phoneNumber == null) {
            // get phone number
        }

        return phoneNumber
    }
}