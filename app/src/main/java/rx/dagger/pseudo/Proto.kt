package rx.dagger.pseudo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    suspend fun protoSendAnything(
        nextState: ProtoFormVisibilityState
    ): String? = suspendCancellableCoroutine { cont ->
        _loading.value = true

        val result = listOf(null, "backend error.").random()
        if (result == null)
            _currentVisibility.value = nextState
            cont.resume(null)
    }
}
