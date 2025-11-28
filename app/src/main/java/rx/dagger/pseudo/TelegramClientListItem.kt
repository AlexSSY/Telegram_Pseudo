package rx.dagger.pseudo

import rx.dagger.pseudo.IndicatorValue

data class TelegramClientListItem(
    val avatarPath: String?,
    val fullName: String?,
    val phoneNumber: String?,
    val indicatorValue: IndicatorValue = IndicatorValue.UNDEFINED
)