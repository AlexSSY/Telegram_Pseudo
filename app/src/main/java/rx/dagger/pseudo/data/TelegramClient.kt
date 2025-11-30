package rx.dagger.pseudo.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val phoneNumberFlow = MutableStateFlow<String?>(null)
    private val fullNameFlow = MutableStateFlow<String?>(null)
    val phoneNumber = phoneNumberFlow.asStateFlow()
    val fullName = fullNameFlow.asStateFlow()


    private val authorizationStateFlow = MutableStateFlow<TdApi.AuthorizationState?>(null)
    val authorizationState = authorizationStateFlow.asStateFlow()

    private fun updateHandler(update: TdApi.Object) {
        when (update.constructor) {
            TdApi.UpdateAuthorizationState.CONSTRUCTOR -> {
                val authorizationState =
                    (update as TdApi.UpdateAuthorizationState).authorizationState
                onAuthorizationStateUpdated(authorizationState)
            }
        }
    }

    private fun onAuthorizationStateUpdated(authorizationState: TdApi.AuthorizationState) {
        authorizationStateFlow.value = authorizationState

        when (authorizationState.constructor) {
            TdApi.AuthorizationStateReady.CONSTRUCTOR -> {
                client.send(TdApi.GetMe(), { obj ->
                    when (obj.constructor) {
                        TdApi.User.CONSTRUCTOR -> {
                            val me = obj as TdApi.User
                            val firstName = me.firstName
                            val lastName = me.lastName
                            val phoneNumber = me.phoneNumber
                            fullNameFlow.value = "$firstName $lastName".trim()
                            phoneNumberFlow.value = phoneNumber
                        }
                        TdApi.Error.CONSTRUCTOR -> {
                            val error = obj as TdApi.Error
                            error("GetMe: $error")
                        }
                    }
                })
            }
        }
    }

    private fun updateExceptionHandler(exception: Throwable) {
        val message = exception.message ?: "unknown"
        error("Update: $message")
    }

    private fun defaultExceptionHandler(exception: Throwable) {
        val message = exception.message ?: "unknown"
        error("Default: $message")
    }
}