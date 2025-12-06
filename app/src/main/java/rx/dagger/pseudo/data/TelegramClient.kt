package rx.dagger.pseudo.data

import android.os.Build
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi
import kotlin.coroutines.resume
import kotlin.text.trim

class TelegramClient(
    private val databaseDirectory: String
) {
    companion object {
        fun error(message: String) {
            System.err.println("TelegramClient -> $message")
        }
    }

    val databaseDirectorySafe: String
        get() = databaseDirectory

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

    var onAuthorizedCallback: (() -> Unit)? = null

    private fun onAuthorizationStateUpdated(authorizationState: TdApi.AuthorizationState) {
        authorizationStateFlow.value = authorizationState

        when (authorizationState.constructor) {
            TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR -> {
                val request = TdApi.SetTdlibParameters()
                request.databaseDirectory = databaseDirectorySafe
                request.apiId = 11721693
                request.apiHash = "e412ffeed9408ed2f3525736cf579ebf"
                request.applicationVersion = "0.1b"
                request.systemLanguageCode = "ru"
                request.deviceModel = Build.MODEL
                client.send(request, { obj ->
                    when (obj.constructor) {
                        TdApi.Ok.CONSTRUCTOR -> {

                        }
                        TdApi.Error.CONSTRUCTOR -> {
                            val error = obj as TdApi.Error
                            error("SetTdlibParameters: $error")
                        }
                    }
                })
            }
            TdApi.AuthorizationStateReady.CONSTRUCTOR -> {
                onAuthorizedCallback?.invoke()
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

    private fun mapTdError(error: TdApi.Error): TelegramError =
        when {
            error.message.contains("PHONE_CODE_INVALID", ignoreCase = true) ->
                TelegramError.InvalidPhoneNumber

            error.message.contains("PHONE_NUMBER_INVALID", ignoreCase = true) ->
                TelegramError.InvalidPhoneNumber

            error.message.startsWith("FLOOD_WAIT_") ->
                TelegramError.FloodWait(
                    error.message.substringAfter("FLOOD_WAIT_").toInt()
                )

            error.message.startsWith("Too Many Requests:") ->
                TelegramError.FloodWait(
                    error.message.substringAfter("Too Many Requests: retry after ").toInt()
                )

            else ->
                TelegramError.Unknown(error.code, error.message)
        }

    private fun requestHandler(
        cont: CancellableContinuation<TelegramResult<Unit>>
    ): (obj: TdApi.Object) -> Unit {
        return { obj ->
            when (obj.constructor) {
                TdApi.Error.CONSTRUCTOR -> {
                    val error = obj as TdApi.Error
                    cont.resume(
                        TelegramResult.Failure(
                            mapTdError(error)
                        )
                    )
                }
                TdApi.Ok.CONSTRUCTOR -> {
                    cont.resume(
                        TelegramResult.Success(Unit)
                    )
                }
            }
        }
    }

    suspend fun sendPhoneNumber(
        phoneNumber: String
    ): TelegramResult<Unit> = suspendCancellableCoroutine { cont ->
        val phoneNumber = phoneNumber.trim()

        if (phoneNumber.isEmpty()) {
            cont.resume(
                TelegramResult.Failure(TelegramError.Empty)
            )
            return@suspendCancellableCoroutine
        }

        val request = TdApi.SetAuthenticationPhoneNumber(phoneNumber, null)
        client.send(request, requestHandler(cont))
    }

    suspend fun sendVerificationCode(
        code: String
    ): TelegramResult<Unit> = suspendCancellableCoroutine { cont ->
        val code = code.trim()

        if (code.isEmpty()) {
            cont.resume(
                TelegramResult.Failure(TelegramError.Empty)
            )
            return@suspendCancellableCoroutine
        }

        val request = TdApi.CheckAuthenticationCode(code)
        client.send(request, requestHandler(cont))
    }

    suspend fun sendPassword(
        password: String
    ): TelegramResult<Unit> = suspendCancellableCoroutine { cont ->
        val password = password.trim()

        if (password.isEmpty()) {
            cont.resume(
                TelegramResult.Failure(TelegramError.Empty)
            )
            return@suspendCancellableCoroutine
        }

        val request = TdApi.CheckAuthenticationPassword(password)
        client.send(request, requestHandler(cont))
    }
}