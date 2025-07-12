package com.dee.core_ui.base

import androidx.lifecycle.ViewModel
import com.dee.core_ui.components.ErrorDialogModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel : ViewModel() {

    private val _event =
        Channel<AppEvent>(onBufferOverflow = BufferOverflow.DROP_OLDEST, capacity = 0)
    val event = _event.receiveAsFlow()


    open fun showLoading() {
        emitEvent(BaseEvent.OnLoading(true))
    }

    open fun hideLoading() {
        emitEvent(BaseEvent.OnLoading(false))
    }

    open fun showErrorMessage(exception: ErrorDialogModel) {
        emitEvent(BaseEvent.OnError(exception))
    }

    open fun emitEvent(event: AppEvent) {
        _event.trySend(event)
    }


}

