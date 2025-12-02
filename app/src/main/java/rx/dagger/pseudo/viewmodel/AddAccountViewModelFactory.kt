package rx.dagger.pseudo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rx.dagger.pseudo.data.TelegramClientRepository

class AddAccountViewModelFactory(
    private val repo: TelegramClientRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAccountViewModel::class.java)) {
            return AddAccountViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}