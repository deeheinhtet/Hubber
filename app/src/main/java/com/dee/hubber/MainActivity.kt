package com.dee.hubber

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import com.dee.authenication.utils.OAuthCallbackManager
import com.dee.common.AppSharedPreference
import com.dee.core_network.base.NetworkException
import com.dee.core_ui.base.BaseActivity
import com.dee.core_ui.base.BaseViewModel
import com.dee.core_ui.components.StatusBarColorManager
import com.dee.hubber.ui.AppGraph
import com.dee.hubber.ui.navigation.AppScreen
import com.dee.hubber.ui.theme.HubberTheme
import com.dee.hubber.user.domain.model.GithubUserRequest
import com.dee.hubber.user.domain.usecase.GetUsersUseCase
import com.dee.hubber.utils.AuthEventManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var oauthCallbackHandler: OAuthCallbackManager

    @Inject
    lateinit var appSharedPreference: AppSharedPreference

    @Inject
    lateinit var authEventManager: AuthEventManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HubberTheme {
                StatusBarColorManager()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.background
                        )
                ) { _ ->
                    AppGraph(
                        initialScreen = if (
                            appSharedPreference.token.isNullOrEmpty()) AppScreen.Login else AppScreen.Dashboard,
                        authEventManager = authEventManager
                    )
                }
            }
        }
        handleOAuthCallback(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleOAuthCallback(intent)
    }

    private fun handleOAuthCallback(intent: Intent?) {
        val uri = intent?.data
        if (uri != null && uri.scheme == "hubber" && uri.host == "callback") {
            oauthCallbackHandler.setOAuthUri(uri)
        }
    }

    private val tokenExpiredBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        oauthCallbackHandler.clearOAuthUri()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HubberTheme {
        Greeting("Android")
    }
}

@HiltViewModel
class MainViewModel @Inject constructor(private val usersUseCase: GetUsersUseCase) :
    BaseViewModel() {


    fun getUsers() {
        viewModelScope.launch {
            val result = usersUseCase.invoke(GithubUserRequest())
            Log.d("RESULT ", "USER ${result}")
            when {
                result.isSuccess -> {
                    val data = result.getOrNull()!!
                }

                result.isFailure -> {
                    val exception = result.exceptionOrNull()
                    (exception as? NetworkException)?.let { networkException ->
//                        _errorEvent.value = networkException
                    }
                }
            }
        }
    }

    fun handleError(exception: NetworkException?) {
        when (exception) {
            is NetworkException.RateLimitException -> {
                showRateLimitError(exception.resetTime)
            }

            is NetworkException.NoInternetException -> {
                showNetworkError()
            }

            is NetworkException.UnauthorizedException -> {
                showAuthError()
            }

            is NetworkException.ValidationException -> {
                showValidationError(exception.errorMessage)
            }

            is NetworkException.HttpException -> {

            }

            is NetworkException.TimeoutException -> {
                // showTimeoutError()
            }

            is NetworkException.SerializationException -> {
                //showDataParsingError()
            }

            else -> {
                showGenericError(exception?.message ?: "Unknown error occurred")
            }
        }
    }

    private fun showRateLimitError(resetTime: Long?) {
        // Handle rate limit specific UI
    }

    private fun showNetworkError() {
        // Handle network error UI
    }

    private fun showAuthError() {
        // Handle auth error UI
    }

    private fun showValidationError(message: String) {
        // Handle validation error UI
    }

    private fun showGenericError(message: String) {
        // Handle generic error UI
    }
}