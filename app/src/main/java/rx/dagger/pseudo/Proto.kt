package rx.dagger.pseudo

import kotlinx.coroutines.delay

enum class ProtoFormVisibilityState {
    PHONE,
    CODE,
    PASSWORD
}

var currentVisibility = ProtoFormVisibilityState.PHONE

suspend fun protoSendAnything(nextState: ProtoFormVisibilityState): String? {
    delay(3000L)
    val result = listOf(null, "backend error.").random()
    if (result == null)
        currentVisibility = nextState
    return result
}