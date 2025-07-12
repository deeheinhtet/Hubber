package com.dee.hubber.utils

import com.dee.core_ui.base.AppEvent
import com.dee.core_ui.base.BaseEvent
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


class AuthEventManager {
    private val _event =
        Channel<AppEvent>(onBufferOverflow = BufferOverflow.DROP_OLDEST, capacity = 0)
    val event = _event.receiveAsFlow()

    fun emitTokenExpired() {
        _event.trySend(BaseEvent.TokenExpired)
    }
}