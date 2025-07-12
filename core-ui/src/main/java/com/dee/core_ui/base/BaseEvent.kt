package com.dee.core_ui.base

import com.dee.core_ui.components.ErrorDialogModel

interface AppEvent {

}

sealed class BaseEvent : AppEvent {
    data class OnError(val errorModel: ErrorDialogModel) : BaseEvent()
    data class OnLoading(val show: Boolean) : BaseEvent()
    data object TokenExpired : BaseEvent()
}