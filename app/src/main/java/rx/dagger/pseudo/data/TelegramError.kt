package rx.dagger.pseudo.data

sealed class TelegramError {
    data object InvalidPhoneNumber : TelegramError()
    data class FloodWait(val seconds: Int) : TelegramError()
    data object Empty : TelegramError()
    data class Unknown(val code: Int, val message: String) : TelegramError()
}