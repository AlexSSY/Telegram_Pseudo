package rx.dagger.pseudo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

enum class ProtoFormVisibilityState {
    PHONE,
    CODE,
    PASSWORD
}

suspend fun successOrFailure(): Boolean {
    delay(3000L)
    return listOf(true, false).random()
}

class Proto : ViewModel() {
    private val _currentVisibility = MutableStateFlow(ProtoFormVisibilityState.PHONE)
    val currentVisibility = _currentVisibility.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private suspend fun protoSendAnything(
        nextState: ProtoFormVisibilityState
    ): String? {
        val result: String? = if (successOrFailure()) null else "backend error."
        if (result == null) {
            _currentVisibility.value = nextState
            return null
        } else {
            return result
        }
    }

    fun sendPhone(phoneCode: String, phoneNumber: String) {
        _error.value = null

        if (phoneCode.isEmpty() || phoneNumber.isEmpty()) {
            _error.value = "Поле не может быть пустым"
            return
        }

        viewModelScope.launch {
            _loading.value = true
            _error.value = protoSendAnything(ProtoFormVisibilityState.CODE)
            _loading.value = false
        }
    }

    fun sendCode(code: String) {
        _error.value = null

        if (code.isEmpty()) {
            _error.value = "Поле не может быть пустым"
            return
        }

        viewModelScope.launch {
            _loading.value = true
            _error.value = protoSendAnything(ProtoFormVisibilityState.PASSWORD)
            _loading.value = false
        }
    }
}
