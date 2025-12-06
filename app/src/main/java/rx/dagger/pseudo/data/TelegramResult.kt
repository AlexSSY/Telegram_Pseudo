package rx.dagger.pseudo.data

sealed class TelegramResult<out T> {
    data class Success<out T>(val data: T) : TelegramResult<T>()
    data class Failure(val error: TelegramError) : TelegramResult<Nothing>()
}