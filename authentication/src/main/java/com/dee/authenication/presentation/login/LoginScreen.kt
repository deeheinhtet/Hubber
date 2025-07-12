package com.dee.authenication.presentation.login

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dee.authenication.domain.navigation.AuthenticationNavigationActions
import com.dee.core_ui.base.AppEvent
import com.dee.core_ui.base.BaseScreen
import com.dee.core_ui.components.ErrorDialogModel
import com.dee.hubber.authentication.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigationActions: AuthenticationNavigationActions
) {

    val activity = LocalActivity.current

    LaunchedEffect(Unit) {
        viewModel.onInitCheckOAuthUri()
    }

    fun handleEvent(event: AppEvent) {
        when (event) {
            is LoginEvents.OnOAuthStart -> {
                val intent = Intent(Intent.ACTION_VIEW, event.uri)
                activity?.startActivity(intent)
            }

            is LoginEvents.OnOAuthLoginFailed -> {
                viewModel.showErrorMessage(
                    ErrorDialogModel(
                        title = activity?.getString(R.string.title_login_error).orEmpty(),
                        message = event.message,
                        buttonText = activity?.getString(R.string.button_label_okay).orEmpty(),
                    )
                )
            }

            is LoginEvents.OnOAuthLoginSuccess -> {
                navigationActions.navigateToDashboard()
            }
        }
    }

    BaseScreen(
        onHandleEvent = {
            handleEvent(it)
        },
        viewModel = viewModel
    ) {
        LoginContent(
            onAuthStart = { viewModel.onGenerateAuthorizationUrl() },
            onStartExplore = { viewModel.onStartExploreWithDefaultToken() }
        )
    }
}

@Composable
fun LoginContent(
    onAuthStart: () -> Unit = {},
    onStartExplore: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            contentDescription = "Github Icon",
            painter = painterResource(R.drawable.github_mark)

        )
        Spacer(Modifier.height(36.dp))
        Text(
            stringResource(R.string.label_login_title),
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(Modifier.height(16.dp))
        Text(
            stringResource(R.string.label_login_description),
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(Modifier.height(36.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors()
                .copy(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            onClick = {
                onAuthStart()
            },
        ) {
            Text(
                stringResource(R.string.label_connect_github_account),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.label_or))
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
            )
                .copy(containerColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onStartExplore()
            },
        ) {
            Text(
                stringResource(R.string.button_text_start_explore),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewLoginContent() {
    LoginContent()
}