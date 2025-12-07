package rx.dagger.pseudo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rx.dagger.pseudo.data.TelegramClient
import rx.dagger.pseudo.data.TelegramClientRepository
import rx.dagger.pseudo.data.TelegramError
import rx.dagger.pseudo.data.TelegramResult

class AddAccountViewModel(
    private val telegramClientRepository: TelegramClientRepository
): ViewModel() {
    val telegramClient = telegramClientRepository.createTelegramClient()

    private val error = MutableStateFlow<String?>(null)
    val errorSafe = error.asStateFlow()

    private val loading = MutableStateFlow(false)
    val loadingSafe = loading.asStateFlow()

    init {
        telegramClient.onAuthorizedCallback = {
            viewModelScope.launch {
                telegramClientRepository.save(telegramClient)
            }
        }
    }

    fun sendPhoneNumber(iso: String, number: String) {
        // Pre-validation
        if (iso.trim().isEmpty()) {
            error.value = "country code is empty."
            return
        }

        if (number.trim().isEmpty()) {
            error.value = "phone number is empty."
            return
        }

        val phoneNumber = (iso + number).trim()

        viewModelScope.launch {
            loading.value = true
            error.value = null

            // Check is number already added
            if (telegramClientRepository.telegramClients.value.find({ client ->
                client.phoneNumber.value == phoneNumber
            }) != null) {
                error.value = "already exists."
                loading.value = false
                return@launch
            }

            val result = telegramClient.sendPhoneNumber(phoneNumber)
            when (result) {
                is TelegramResult.Failure -> {
                    when (result.error) {
                        TelegramError.Empty -> {
                            error.value = "can not be empty."
                        }
                        is TelegramError.FloodWait -> {
                            error.value = "too many requests please wait ${result.error.seconds} seconds."
                        }
                        TelegramError.InvalidPhoneNumber -> {
                            error.value = "invalid phone number."
                        }
                        is TelegramError.Unknown -> {
                            error.value = "unknown error."
                        }
                    }
                }
                is TelegramResult.Success<Unit> -> {

                }
            }

            loading.value = false
        }
    }

    fun sendCode(code: String) {
        viewModelScope.launch {
            loading.value = true
            error.value = null
            val result = telegramClient.sendVerificationCode(code)
            when (result) {
                is TelegramResult.Failure -> {
                    when (result.error) {
                        TelegramError.Empty -> {
                            error.value = "can not be empty."
                        }
                        is TelegramError.FloodWait -> {
                            error.value = "too many requests please wait ${result.error.seconds} seconds."
                        }
                        TelegramError.InvalidPhoneNumber -> {
                            error.value = "invalid code."
                        }
                        is TelegramError.Unknown -> {
                            error.value = "unknown error."
                        }
                    }
                }
                is TelegramResult.Success<Unit> -> {

                }
            }
            loading.value = false
        }
    }

    fun sendPassword(password: String) {
        viewModelScope.launch {
            loading.value = true
            error.value = null
            val result = telegramClient.sendPassword(password)
            when (result) {
                is TelegramResult.Failure -> {
                    when (result.error) {
                        TelegramError.Empty -> {
                            error.value = "can not be empty."
                        }
                        is TelegramError.FloodWait -> {
                            error.value = "too many requests please wait ${result.error.seconds} seconds."
                        }
                        TelegramError.InvalidPhoneNumber -> {
                            error.value = "invalid code."
                        }
                        is TelegramError.Unknown -> {
                            error.value = "unknown error."
                        }
                    }
                }
                is TelegramResult.Success<Unit> -> {

                }
            }
            loading.value = false
        }
    }
}