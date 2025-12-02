package rx.dagger.pseudo.viewmodel

import androidx.lifecycle.ViewModel
import rx.dagger.pseudo.data.TelegramClientRepository

class AddAccountViewModel(
    private val telegramClientRepository: TelegramClientRepository
): ViewModel() {

}