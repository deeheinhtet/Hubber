package com.dee.core_ui.base

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dee.core_ui.components.ErrorDialog
import com.dee.core_ui.components.ErrorDialogModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel,
    onHandleEvent: (event: AppEvent) -> Unit = {},
    content: @Composable () -> Unit,
) {
    var errorModel by remember { mutableStateOf<ErrorDialogModel?>(null) }
    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest {
            Log.d("BaseScreen: ", "Trigger event ${it}")
            when (it) {
                is BaseEvent.OnError -> {
                    errorModel = it.errorModel
                }

                is BaseEvent.OnLoading -> {
                    showLoading = it.show
                }

                else -> onHandleEvent(it)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        content()
        errorModel?.let { model ->
            ErrorDialog(
                model = model,
                onDismiss = { errorModel = null }
            )
        }
        if (showLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}

